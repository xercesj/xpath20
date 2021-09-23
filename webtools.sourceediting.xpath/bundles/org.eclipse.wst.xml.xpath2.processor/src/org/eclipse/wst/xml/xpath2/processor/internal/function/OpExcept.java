/*******************************************************************************
 * Copyright (c) 2005, 2011 Andrea Bittau, University College London, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andrea Bittau - initial API and implementation from the PsychoPath XPath 2.0 
 *     Jesper Steen Moeller - bug 285145 - implement full arity checking
 *     Mukul Gandhi - bug 280798 - PsychoPath support for JDK 1.4
 *     Jesper Steen Moller  - bug 316988 - Removed O(n^2) performance for large results
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.function;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.TreeSet;

import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.internal.SeqType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.NodeType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.QName;
import org.eclipse.wst.xml.xpath2.processor.internal.utils.ResultSequenceUtil;

/**
 * Support for Except operation on node types.
 */
public class OpExcept extends Function {
	private static Collection _expected_args = null;

	/**
	 * Constructor for OpExcept.
	 */
	public OpExcept() {
		super(new QName("except"), 2);
	}

	/**
	 * Evaluate arguments.
	 * 
	 * @param args
	 *            argument expressions.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of evaluation.
	 */
	public ResultSequence evaluate(Collection args) throws DynamicError {
		assert args.size() >= min_arity() && args.size() <= max_arity();

		return op_except(args);
	}

	/**
	 * Op-Except operation.
	 * 
	 * @param args
	 *            Result from the expressions evaluation.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of operation.
	 */
	public static ResultSequence op_except(Collection args) throws DynamicError {
		// convert arguments
		Collection cargs = Function.convert_arguments(args, expected_args());

		// get arguments
		Iterator iter = cargs.iterator();
		ResultSequence one = (ResultSequence) iter.next();
		ResultSequence two = (ResultSequence) iter.next();
		
		TreeSet all = new TreeSet(NodeType.NODE_COMPARATOR);
		for (ListIterator itOne = one.iterator(); itOne.hasNext(); ) all.add(itOne.next());
		for (ListIterator itTwo = two.iterator(); itTwo.hasNext(); ) all.remove(itTwo.next());

		return ResultSequenceUtil.resultSequenceFromCollection(all);
	}

	/**
	 * Obtain a list of expected arguments.
	 * 
	 * @return Result of operation.
	 */
	public synchronized static Collection expected_args() {
		if (_expected_args == null) {
			_expected_args = new ArrayList();

			SeqType st = new SeqType(SeqType.OCC_STAR);

			_expected_args.add(st);
			_expected_args.add(st);
		}
		return _expected_args;
	}
}
