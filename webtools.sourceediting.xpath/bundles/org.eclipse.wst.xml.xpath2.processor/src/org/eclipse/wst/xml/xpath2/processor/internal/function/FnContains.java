/*******************************************************************************
 * Copyright (c) 2005, 2010 Andrea Bittau, University College London, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andrea Bittau - initial API and implementation from the PsychoPath XPath 2.0
 *     David Carver - STAR - bug 262765 - check for empty string in second arg before first. 
 *     Mukul Gandhi - bug 280798 - PsychoPath XPath 2.0 support for JDK 1.4
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.function;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;
import org.eclipse.wst.xml.xpath2.processor.internal.SeqType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.QName;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSBoolean;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSString;

/**
 * Returns an xs:boolean indicating whether or not the value of $arg1 contains
 * (at the beginning, at the end, or anywhere within) at least one sequence of
 * collation units that provides a minimal match to the collation units in the
 * value of $arg2, according to the collation that is used.
 */
public class FnContains extends Function {
	private static Collection _expected_args = null;

	/**
	 * Constructor for FnContains.
	 */
	public FnContains() {
		super(new QName("contains"), 2);
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
		return contains(args);
	}

	/**
	 * Contains operation.
	 * 
	 * @param args
	 *            Result from the expressions evaluation.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of fn:contains operation.
	 */
	public static ResultSequence contains(Collection args) throws DynamicError {
		Collection cargs = Function.convert_arguments(args, expected_args());

		ResultSequence rs = ResultSequenceFactory.create_new();

		// get args
		Iterator argiter = cargs.iterator();
		ResultSequence arg1 = (ResultSequence) argiter.next();
		String str1 = "";
		String str2 = "";
		if (!arg1.empty())
			str1 = ((XSString) arg1.first()).value();

		ResultSequence arg2 = (ResultSequence) argiter.next();
		if (!arg2.empty())
			str2 = ((XSString) arg2.first()).value();

		int str1len = str1.length();
		int str2len = str2.length();

		if (str2len == 0) {
			rs.add(new XSBoolean(true));
			return rs;
		}
		
		if (str1len == 0) {
			rs.add(new XSBoolean(false));
			return rs;
		}

		if (str1.indexOf(str2) == -1)
			rs.add(new XSBoolean(false));
		else
			rs.add(new XSBoolean(true));

		return rs;
	}

	/**
	 * Obtain a list of expected arguments.
	 * 
	 * @return Result of operation.
	 */
	public synchronized static Collection expected_args() {
		if (_expected_args == null) {
			_expected_args = new ArrayList();
			SeqType arg = new SeqType(new XSString(), SeqType.OCC_QMARK);
			_expected_args.add(arg);
			_expected_args.add(arg);
		}

		return _expected_args;
	}
}
