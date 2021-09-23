/*******************************************************************************
 * Copyright (c) 2005, 2010 Andrea Bittau, University College London, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andrea Bittau - initial API and implementation from the PsychoPath XPath 2.0
 *     Mukul Gandhi - improvements to the function implementation
 *     David Carver - bug 282096 - improvements for surrogate handling 
 *     Jesper Steen Moeller - bug 282096 - clean up string storage
 *     Jesper Steen Moeller - bug 280553 - further checks of legal Unicode codepoints.
 *     Mukul Gandhi - bug 280798 - PsychoPath support for JDK 1.4
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.function;

import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;
import org.eclipse.wst.xml.xpath2.processor.internal.SeqType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.QName;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSInteger;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.ibm.icu.text.UTF16;

/**
 * Creates an xs:string from a sequence of code points. Returns the zero-length
 * string if $arg is the empty sequence. If any of the code points in $arg is
 * not a legal XML character, an error is raised [err:FOCH0001].
 */
public class FnCodepointsToString extends Function {
	private static Collection _expected_args = null;
	
    /**
     * The maximum value of a Unicode code point.
     */
    public static final int MIN_LEGAL_CODEPOINT = 0x1;


    /**
     * The maximum value of a Unicode code point.
     */
    public static final int MAX_LEGAL_CODEPOINT = 0x10ffff;

	/**
	 * Constructor for FnCodepointsToString.
	 */
	public FnCodepointsToString() {
		super(new QName("codepoints-to-string"), 1);
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
		return codepoints_to_string(args);
	}

	/**
	 * Codepoints to string operation.
	 * 
	 * @param args
	 *            Result from the expressions evaluation.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of fn:codepoints-to-string operation.
	 */
	public static ResultSequence codepoints_to_string(Collection args)
			throws DynamicError {
		Collection cargs = Function.convert_arguments(args, expected_args());

		ResultSequence rs = ResultSequenceFactory.create_new();

		ResultSequence arg1 = (ResultSequence) cargs.iterator().next();
		if (arg1.empty()) {
			rs.add(new XSString(""));
			return rs;
		}

		int[] codePointArray = new int[arg1.size()];
		int codePointIndex = 0;
		for (Iterator i = arg1.iterator(); i.hasNext();) {
			XSInteger code = (XSInteger) i.next();
			
			int codepoint = code.int_value().intValue();
			if (codepoint < MIN_LEGAL_CODEPOINT || codepoint > MAX_LEGAL_CODEPOINT) {
				throw DynamicError.unsupported_codepoint("U+" + Integer.toString(codepoint, 16).toUpperCase());
			}

			codePointArray[codePointIndex] = codepoint;			
			codePointIndex++;
		}

		try {
			String str = UTF16.newString(codePointArray, 0, codePointArray.length);
			rs.add(new XSString(str));
		} catch (IllegalArgumentException iae) {
			// This should be duoble checked above, but rather safe than sorry
			throw DynamicError.unsupported_codepoint(iae.getMessage());
		}
		
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
			_expected_args.add(new SeqType(new XSInteger(), SeqType.OCC_STAR));
		}

		return _expected_args;
	}
}
