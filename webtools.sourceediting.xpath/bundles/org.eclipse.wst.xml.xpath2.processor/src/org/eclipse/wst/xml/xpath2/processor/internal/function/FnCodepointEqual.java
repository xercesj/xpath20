/*******************************************************************************
 * Copyright (c) 2009, 2010 Jesper Steen Moller, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jesper Steen Moller - initial API and implementation
 *     Mukul Gandhi - bug 280798 - PsychoPath support for JDK 1.4
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.function;

import org.eclipse.wst.xml.xpath2.processor.DynamicContext;
import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;
import org.eclipse.wst.xml.xpath2.processor.internal.SeqType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.QName;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSBoolean;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSString;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * 
 * <p>
 * String comparison function.
 * </p>
 * 
 * <p>
 * Usage: fn:codepoint-equal($comparand1 as xs:string?,
 *                           $comparand2 as xs:string?) as xs:boolean?
 * </p>
 * 
 * <p>
 * Returns true or false depending on whether the value of $comparand1 is equal
 * to the value of $comparand2, according to the Unicode code point collation
 * (http://www.w3.org/2005/xpath-functions/collation/codepoint).
 * </p>
 * 
 * <p>
 * If either argument is the empty sequence, the result is the empty sequence.
 * </p>
 * 
 */
public class FnCodepointEqual extends Function {
	
	private static Collection _expected_args = null;

	/**
	 * Constructor of FnCodepointEqual.
	 */
	public FnCodepointEqual() {
		super(new QName("codepoint-equal"), 2);
	}

	/**
	 * Evaluate the arguments.
	 * 
	 * @param args
	 *            is evaluated.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return The evaluation of the comparison of the arguments.
	 */
	public ResultSequence evaluate(Collection args) throws DynamicError {
		return codepoint_equals(args, dynamic_context());
	}

	/**
	 * Compare the arguments as codepoints
	 * 
	 * @param args
	 *            are compared.
	 * @param dynamicContext
	 *            The current dynamic context
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return The result of the comparison of the arguments.
	 */
	public static ResultSequence codepoint_equals(Collection args, DynamicContext dynamicContext) throws DynamicError {
		Collection cargs = Function.convert_arguments(args, expected_args());

		ResultSequence rs = ResultSequenceFactory.create_new();

		Iterator argiter = cargs.iterator();
		ResultSequence arg1 = (ResultSequence) argiter.next();
		XSString xstr1 = arg1.empty() ? null : (XSString) arg1.first();

		ResultSequence arg2 = (ResultSequence) argiter.next();
		XSString xstr2 = arg2.empty() ? null : (XSString) arg2.first();

		// This delegates to FnCompare
		BigInteger result = FnCompare.compare_string(DynamicContext.CODEPOINT_COLLATION, xstr1, xstr2, dynamicContext);
		if (result != null) rs.add(new XSBoolean(BigInteger.ZERO.equals(result)));
		
		return rs;
	}

	/**
	 * Calculate the expected arguments.
	 * 
	 * @return The expected arguments.
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
