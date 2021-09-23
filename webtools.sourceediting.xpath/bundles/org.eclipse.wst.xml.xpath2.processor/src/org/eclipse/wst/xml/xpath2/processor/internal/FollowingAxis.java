/*******************************************************************************
 * Copyright (c) 2005, 2011 Andrea Bittau, University College London, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andrea Bittau - initial API and implementation from the PsychoPath XPath 2.0 
 *     Jesper Steen Moller  - bug 316988 - Removed O(n^2) performance for large results
 *     Mukul Gandhi  - bug 353373 - "preceding" & "following" axes behavior is erroneous
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal;

import org.eclipse.wst.xml.xpath2.processor.DynamicContext;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;
import org.eclipse.wst.xml.xpath2.processor.internal.types.NodeType;

import java.util.Iterator;
import java.util.ListIterator;

/**
 * the following axis contains the context node's following siblings, those
 * children of the context node's parent that occur after the context node in
 * document order.
 */
public class FollowingAxis extends ForwardAxis {

	/**
	 * Return the result of FollowingAxis expression
	 * 
	 * @param node
	 *            is the type of node.
	 * @param dc
	 *            is the dynamic context.
	 * @return The result of FollowingAxis.
	 */
	public ResultSequence iterate(NodeType node, DynamicContext dc) {
		ResultSequence result = ResultSequenceFactory.create_new();

		// get the parent
		NodeType parent = null;
		ParentAxis pa = new ParentAxis();
		ResultSequence rs = pa.iterate(node, dc);
		if (rs.size() == 1)
			parent = (NodeType) rs.get(0);

		DescendantAxis da = new DescendantAxis();

		// get the following siblings & their descendants for this node and add them
		FollowingSiblingAxis fsa = new FollowingSiblingAxis();
		rs = fsa.iterate(node, dc);
		for (Iterator i = rs.iterator(); i.hasNext();) {
			NodeType followingSiblingTemp = (NodeType) i.next();
			result.add(followingSiblingTemp);
			ResultSequence desc = da.iterate(followingSiblingTemp, dc);
			result.concat(desc);
		}

		// if we got a parent, we gotta repeat the story for the parent and add the results
		if (parent != null) {
			rs = iterate(parent, dc);
			result.concat(rs);
		}

		return result;
	}
	
	/**
	 * Retrieves the context node's attributes.
	 * 
	 * @param node
	 *            is the type of node.
	 * @param dc
	 *            is the dynamic context.
	 * @destination The context node and its descendants.
	 */
	protected void collect(NodeType node, DynamicContext dc,
		ListIterator destination) {
		
	}
}
