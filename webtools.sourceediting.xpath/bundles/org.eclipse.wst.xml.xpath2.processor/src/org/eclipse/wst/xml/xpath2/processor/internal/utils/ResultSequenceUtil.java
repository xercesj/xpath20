/*******************************************************************************
 * Copyright (c) 2011 Jesper Steen Moller, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jesper Steen Moller - initial API and implementation
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;

public class ResultSequenceUtil {

	public static ResultSequence resultSequenceFromCollection(Collection coll) {
		ResultSequence rs = ResultSequenceFactory.create_new();

		ListIterator outIterator = rs.iterator();
		for (Iterator sortedIt = coll.iterator(); sortedIt.hasNext(); ) {
			outIterator.add(sortedIt.next());
		}
		return rs;
	}

	public static void copyToCollection(ListIterator it, Collection destination) {
		for (; it.hasNext();) {
			destination.add(it.next());
		}
	}
	
}
