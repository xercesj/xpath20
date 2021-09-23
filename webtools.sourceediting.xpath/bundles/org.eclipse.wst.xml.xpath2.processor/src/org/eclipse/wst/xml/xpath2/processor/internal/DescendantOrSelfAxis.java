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
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal;


import java.util.ListIterator;

import org.eclipse.wst.xml.xpath2.processor.DynamicContext;
import org.eclipse.wst.xml.xpath2.processor.internal.types.NodeType;

/**
 * The descendant-or-self axis contains the context node and the descendants of
 * the context node.
 */
// multiple inheretance might be cool here =D
public class DescendantOrSelfAxis extends ForwardAxis {

	/**
	 * Retrieve the the descendants of the context node and the context node
	 * itself.
	 * 
	 * @param node
	 *            is the type of node.
	 * @param dc
	 *            is the dynamic context.
	 * @destination The context node and its descendants.
	 */
	protected void collect(NodeType node, DynamicContext dc,
			ListIterator destination) {

		destination.add(node);
		DescendantAxis da = new DescendantAxis();
		da.collect(node, dc, destination);
	}
	

}
