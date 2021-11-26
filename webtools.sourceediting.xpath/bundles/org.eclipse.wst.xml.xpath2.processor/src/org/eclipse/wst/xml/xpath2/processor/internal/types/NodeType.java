/*******************************************************************************
 * Copyright (c) 2005, 2011 Andrea Bittau, University College London, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andrea Bittau - initial API and implementation from the PsychoPath XPath 2.0
 *     Mukul Gandhi - bug 276134 - improvements to schema aware primitive type support
 *                                 for attribute/element nodes
 *     David Carver (STAR)- bug 277774 - XSDecimal returning wrong values.
 *     Jesper Moller - bug 275610 - Avoid big time and memory overhead for externals
 *     David Carver (STAR) - bug 281186 - implementation of fn:id and fn:idref
 *     David Carver (STAR) - bug 289304 - fixed schema awareness on elements
 *     Mukul Gandhi - bug 318313 - improvements to computation of typed values of nodes,
 *                                 when validated by XML Schema primitive types
 *     Mukul Gandhi - bug 323900 - improving computing the typed value of element & attribute nodes, where the schema type of nodes
 *                                 are simple, with varieties 'list' and 'union'.                                 
 *     Jesper Moller - bug 316988 - Removed O(n^2) performance for large results
 *     Mukul Gandhi  - bug 343224 - allow user defined simpleType definitions to be available in in-scope schema types
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.types;

import java.util.Comparator;
import java.util.TreeSet;

import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.xs.ShortList;
import org.apache.xerces.xs.XSComplexTypeDefinition;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;
import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;
import org.eclipse.wst.xml.xpath2.processor.XPath20TypeHelper;
import org.eclipse.wst.xml.xpath2.processor.internal.utils.ResultSequenceUtil;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.TypeInfo;

/**
 * A representation of a Node datatype
 */
public abstract class NodeType extends AnyType {
	
	protected static final String SCHEMA_TYPE_IDREF = "IDREF";
	protected static final String SCHEMA_TYPE_ID = "ID";
	private Node _node;

	public static final Comparator NODE_COMPARATOR = new Comparator() {
		public int compare(Object o1, Object o2) {
			return compare_node((NodeType)o1, (NodeType)o2);
		}
	};
	
	/**
	 * Initialises according to the supplied parameters
	 * 
	 * @param node
	 *            The Node being represented
	 * @param document_order
	 *            The document order
	 */
	public NodeType(Node node) {
		_node = node;
	}

	/**
	 * Retrieves the actual node being represented
	 * 
	 * @return Actual node being represented
	 */
	public Node node_value() {
		return _node;
	}

	// Accessors defined in XPath Data model
	// http://www.w3.org/TR/xpath-datamodel/
	/**
	 * Retrieves the actual node being represented
	 * 
	 * @return Actual node being represented
	 */
	public abstract ResultSequence typed_value() throws DynamicError;

	/**
	 * Retrieves the name of the node
	 * 
	 * @return QName representation of the name of the node
	 */
	public abstract QName node_name(); // may return null ["empty sequence"]

	// XXX element should override
	public ResultSequence nilled() {
		return ResultSequenceFactory.create_new();
	}

	// a little factory for converting from DOM to our representation
	public static NodeType dom_to_xpath(Node node) {
		assert node != null;
		
		switch (node.getNodeType()) {
		case Node.ELEMENT_NODE:
			return new ElementType((Element) node);

		case Node.COMMENT_NODE:
			return new CommentType((Comment) node);

		case Node.ATTRIBUTE_NODE:
			return new AttrType((Attr) node);

		case Node.TEXT_NODE:
			return new TextType((Text) node);

		case Node.DOCUMENT_NODE:
			return new DocType((Document) node);

		case Node.PROCESSING_INSTRUCTION_NODE:
			return new PIType((ProcessingInstruction) node);

			// XXX
		default:
			assert false;

		}

		// unreach... hopefully
		return null;
	}

	public static ResultSequence linearize(ResultSequence rs) {
		TreeSet all = new TreeSet(NODE_COMPARATOR);
		ResultSequenceUtil.copyToCollection(rs.iterator(), all);
		return ResultSequenceUtil.resultSequenceFromCollection(all);
	}

	public static boolean same(NodeType a, NodeType b) {
		return (a.node_value().isSameNode(b.node_value()));
		// While compare_node(a, b) == 0 is tempting, it is also expensive
	}

	public boolean before(NodeType two) {
		return before(this, two);
	}

	public static boolean before(NodeType a, NodeType b) {
		return compare_node(a, b) < 0;
	}

	public boolean after(NodeType two) {
		return after(this, two);
	}

	public static boolean after(NodeType a, NodeType b) {
		return compare_node(a, b) > 0;
	}
	
	private static int compare_node(NodeType a, NodeType b) {
		Node nodeA = a.node_value();
		Node nodeB = b.node_value();
		
		if (nodeA == nodeB || nodeA.isSameNode(nodeB)) return 0;

		Document docA = getDocument(nodeA);
		Document docB = getDocument(nodeB);
		
		if (docA != docB) {
			return compareDocuments(docA, docB);
		}
		short relation = nodeA.compareDocumentPosition(nodeB);
		if ((relation & Node.DOCUMENT_POSITION_PRECEDING) != 0) 
			  return 1;
		if ((relation & Node.DOCUMENT_POSITION_FOLLOWING) != 0) 
			  return -1;
		throw new RuntimeException("Unexpected result from node comparison: " + relation);
	}

	/**
	 * Compare two documents (node ordering wise). Uses information from the DOM itself if applicable,
	 * otherwise uses document URI, or if they are identical, compare by hash code.
	 * 
	 * @param docA First document
	 * @param docB Second document
	 * @return negative, zero, or positive like {@link Comparable#compareTo(Object)}
	 */
	private static int compareDocuments(Document docA, Document docB) {
		if (docA.isSameNode(docB)) return 0; // Object alias for same underlying node (proxy?)
		
		// Arbitrary but fulfills the spec (provided documenURI is always set)
		if (docA.getDocumentURI() != null && docB.getDocumentURI() != null) {
			int difference = docB.getDocumentURI().compareTo(docA.getDocumentURI());
			if (difference != 0) return difference;
		}
		// Last resort
		return docA.hashCode() - docB.hashCode();
	}

	private static Document getDocument(Node nodeA) {
		return nodeA instanceof Document ? (Document)nodeA : nodeA.getOwnerDocument();
	}

	protected AnyType getTypedValueForPrimitiveType(XSTypeDefinition typeDef) {		
		String strValue = string_value();
		
		if (typeDef == null) {
		   return new XSUntypedAtomic(strValue);
		}
		
		return SchemaTypeValueFactory.newSchemaTypeValue(XPath20TypeHelper.getXSDTypeShortCode(typeDef), strValue);
		
	} // getTypedValueForPrimitiveType 

	
	/*
	 * Construct the "typed value" from a "string value", given the simpleType of the node.
     */
	protected ResultSequence getXDMTypedValue(XSTypeDefinition typeDef, ShortList itemValTypes) {
		
		ResultSequence rs = ResultSequenceFactory.create_new();
		
		if ("anyType".equals(typeDef.getName()) ||
			"anySimpleType".equals(typeDef.getName()) || 
		    "anyAtomicType".equals(typeDef.getName())) {
			rs.add(new XSUntypedAtomic(string_value()));
		}
		else {
			XSSimpleTypeDefinition simpType = null;
			ResultSequence rsSimpleContent = null;

			if (typeDef instanceof XSComplexTypeDefinition) {
				XSComplexTypeDefinition complexTypeDefinition = (XSComplexTypeDefinition) typeDef;
				simpType = complexTypeDefinition.getSimpleType();
				if (simpType != null) {
					// element has a complexType with a simple content
					rsSimpleContent = getTypedValueForSimpleContent(simpType, itemValTypes);
				}
				else {
					// element has a complexType with complex content
					rs.add(new XSUntypedAtomic(string_value()));
				}
			} else {
				// element has a simpleType
				simpType = (XSSimpleTypeDefinition) typeDef;
				rsSimpleContent = getTypedValueForSimpleContent(simpType, itemValTypes);
			}

			if (rsSimpleContent != null) {
				rs =  rsSimpleContent;
			}
		}
			
		return rs;
		
	} // getXDMTypedValue
	
	
    /*
     * Get the XDM typed value for schema "simple content model". 
     */
	private ResultSequence getTypedValueForSimpleContent(XSSimpleTypeDefinition simpType, ShortList itemValueTypes) {
		
		ResultSequence rs = ResultSequenceFactory.create_new();
		
		if (simpType.getVariety() == XSSimpleTypeDefinition.VARIETY_ATOMIC) {
		   AnyType schemaTypeValue = SchemaTypeValueFactory.newSchemaTypeValue(XPath20TypeHelper.getXSDTypeShortCode(simpType), string_value());
		   if (schemaTypeValue != null) {
				rs.add(schemaTypeValue);
		   } else {
				rs.add(new XSUntypedAtomic(string_value()));
		   }
		}
		else if (simpType.getVariety() == XSSimpleTypeDefinition.VARIETY_LIST) {
			addAtomicListItemsToResultSet(simpType, itemValueTypes, rs);
		}
		else if (simpType.getVariety() == XSSimpleTypeDefinition.VARIETY_UNION) {
			getTypedValueForVarietyUnion(simpType, itemValueTypes, rs);
		}
		
		return rs;
		
	} // getTypedValueForSimpleContent
	
	
	/*
	 * If the variety of simpleType was 'list', add the typed "list item" values to the parent result set. 
	 */
	private void addAtomicListItemsToResultSet(XSSimpleTypeDefinition simpType, ShortList itemValueTypes, ResultSequence rs) {
		
		// tokenize the string value by a 'longest sequence' of white-spaces. this gives us the list items as string values.
		String[] listItemsStrValues = string_value().split("\\s+");
		
		XSSimpleTypeDefinition itemType = simpType.getItemType();		
		if (itemType.getVariety() == XSSimpleTypeDefinition.VARIETY_ATOMIC) {
			for (int listItemIdx = 0; listItemIdx < listItemsStrValues.length; listItemIdx++) {
			   // add an atomic typed value (whose type is the "item  type" of the list, and "string value" is the "string 
			   // value of the list item") to the "result sequence".
		       rs.add(SchemaTypeValueFactory.newSchemaTypeValue(XPath20TypeHelper.getXSDTypeShortCode(itemType), listItemsStrValues[listItemIdx]));
			}
		}
		else if (itemType.getVariety() == XSSimpleTypeDefinition.VARIETY_UNION) {
		    // here the list items may have different atomic types
			for (int listItemIdx = 0; listItemIdx < listItemsStrValues.length; listItemIdx++) {
				String listItem = listItemsStrValues[listItemIdx];
				rs.add(SchemaTypeValueFactory.newSchemaTypeValue(itemValueTypes.item(listItemIdx), listItem));
			}
		}
		
	} // addAtomicListItemsToResultSet
	
	
	/*
	 * If the variety of simpleType was 'union', find the typed value (and added to the parent 'result set') 
	 * to be returned as the typed value of the parent node, by considering the member types of the union (i.e
	 * whichever member type first in order, can successfully validate the string value of the parent node).
	 */
	private void getTypedValueForVarietyUnion(XSSimpleTypeDefinition simpType, ShortList itemValueTypes, ResultSequence rs) {
		
		XSObjectList memberTypes = simpType.getMemberTypes();
		// check member types in order, to find that which one can successfully validate the string value.
		for (int memTypeIdx = 0; memTypeIdx < memberTypes.getLength(); memTypeIdx++) {
		   XSSimpleType memSimpleType = (XSSimpleType) memberTypes.item(memTypeIdx);
		   if (XPath20TypeHelper.isValueValidForSimpleType(string_value(), memSimpleType)) {
			   if (memSimpleType.getVariety() == XSSimpleTypeDefinition.VARIETY_LIST) {
				   addAtomicListItemsToResultSet(memSimpleType, itemValueTypes, rs);
			   }
			   else if (memSimpleType.getVariety() == XSSimpleTypeDefinition.VARIETY_UNION) {
				   getTypedValueForVarietyUnion(memSimpleType, itemValueTypes, rs);
			   }
			   else {
			       rs.add(SchemaTypeValueFactory.newSchemaTypeValue(XPath20TypeHelper.getXSDTypeShortCode(memSimpleType), string_value()));
			   }
			   // no more memberTypes need to be checked
			   break; 
		   }
		}
		
	} // getTypedValueForVarietyUnion
	
	public abstract boolean isID();
	
	public abstract boolean isIDREF();

	/**
	 * Utility method to check to see if a particular TypeInfo matches.
	 * @param typeInfo
	 * @param typeName
	 * @return
	 */
	protected boolean isType(TypeInfo typeInfo, String typeName) {
		if (typeInfo != null) {
			String typeInfoName = typeInfo.getTypeName();
			if (typeInfoName != null) {
				if (typeInfo.getTypeName().equalsIgnoreCase(typeName)) {
					return true;
				}
			} 
		}
		return false;
	}
	
	public boolean equals(Object obj) {
		return obj instanceof NodeType && same(this, (NodeType)obj);
	}
	
	public int hashCode() {
		return _node.hashCode();
	}
	
}
