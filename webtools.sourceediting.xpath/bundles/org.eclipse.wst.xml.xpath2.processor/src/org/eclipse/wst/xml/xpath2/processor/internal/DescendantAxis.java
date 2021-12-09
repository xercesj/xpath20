/*******************************************************************************
 * Copyright (c) 2005, 2011 Andrea Bittau, University College London, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andrea Bittau - initial API and implementation from the PsychoPath XPath 2.0 
 *     Mukul Gandhi - bug 280798 - PsychoPath XPath 2.0 support for JDK 1.4
 *     Jesper Steen Moller  - bug 316988 - Removed O(n^2) performance for large results
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import org.eclipse.wst.xml.xpath2.processor.DynamicContext;
import org.eclipse.wst.xml.xpath2.processor.internal.types.NodeType;

/**
 * The descendant axis contains the descendants of the context node
 */
public class DescendantAxis extends ChildAxis {

	/**
	 * Using the context node retrieve the descendants of this node
	 * 
	 * @param node
	 *            is the type of node.
	 * @param dc
	 *            is the dynamic context.
	 * @return The descendants of the context node.
	 */
	protected void collect(NodeType node, DynamicContext dc,
			ListIterator destination) {
		// get the children
		ArrayList children = new ArrayList();
		super.collect(node, dc, children.listIterator());

		// get descendants of all children
		for (Iterator i = children.iterator(); i.hasNext();) {
			NodeType n = (NodeType) i.next();
			destination.add(n);

			collect(n, dc, destination);
		}
	}

}
