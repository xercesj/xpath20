/*******************************************************************************
 * Copyright (c) 2011 Jesper Steen Moller, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jesper Steen Moller - initial API and implementation from the PsychoPath XPath 2.0 
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal;

import java.util.ListIterator;

import org.eclipse.wst.xml.xpath2.processor.DynamicContext;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;
import org.eclipse.wst.xml.xpath2.processor.internal.types.NodeType;

public abstract class AbstractAxis implements Axis {

	public ResultSequence iterate(NodeType node, DynamicContext dc) {
		ResultSequence rs = ResultSequenceFactory.create_new();
		collect(node, dc, rs.iterator());
		
		return rs;
	}

	protected abstract void collect(NodeType node, DynamicContext dc, ListIterator destination);

}
