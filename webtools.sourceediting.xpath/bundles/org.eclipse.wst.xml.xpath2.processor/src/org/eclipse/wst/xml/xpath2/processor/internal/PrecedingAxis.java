/*******************************************************************************
 * Copyright (c) 2005, 2009 Andrea Bittau, University College London, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andrea Bittau - initial API and implementation from the PsychoPath XPath 2.0
 *     Mukul Gandhi  - bug 353373 - "preceding" & "following" axes behavior is erroneous
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal;

import org.eclipse.wst.xml.xpath2.processor.DynamicContext;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;
import org.eclipse.wst.xml.xpath2.processor.internal.types.NodeType;

import java.util.Iterator;

/**
 * the preceding axis contains all nodes that are descendants of the root of the
 * tree in which the context node is found
 */
public class PrecedingAxis extends ReverseAxis {

	/**
	 * returns preceding nodes of the context node
	 * 
	 * @param node
	 *            is the node type.
	 * @throws dc
	 *             is the Dynamic context.
	 * @return the descendants of the context node
	 */
	public ResultSequence iterate(NodeType node, DynamicContext dc) {
		ResultSequence result = ResultSequenceFactory.create_new();

		// get the parent
		NodeType parent = null;
		ParentAxis pa = new ParentAxis();
		ResultSequence rs = pa.iterate(node, dc);
		if (rs.size() == 1)
			parent = (NodeType) rs.get(0);

		// get the preceding siblings of this node, and add them
		if (parent != null) {
			PrecedingSiblingAxis psa = new PrecedingSiblingAxis();
			rs = psa.iterate(parent, dc);
			
			// for each sibling, get all its descendants
			DescendantAxis da = new DescendantAxis();
			for (Iterator i = rs.iterator(); i.hasNext();) {
				// add firstly this node to the result
				NodeType precedingNodeTmp = (NodeType) i.next();
				result.add(precedingNodeTmp);
				// get descendants of this node, and add them to the result
				ResultSequence desc = da.iterate(precedingNodeTmp, dc);
				result.concat(desc);
			}
			
			// add preceding siblings of original context node & their descendants to the result
			rs = psa.iterate(node, dc);
			for (Iterator i = rs.iterator(); i.hasNext();) {
				NodeType precedingNodeTmp = (NodeType) i.next();
				result.add(precedingNodeTmp);
				ResultSequence desc = da.iterate(precedingNodeTmp, dc);
				result.concat(desc);
			}
			
			// we gotta repeat the story for the parent and add the results
			rs = iterate(parent, dc);
			rs.concat(result);
			result = rs;
		}
		
		return result;
	}
}
