/*******************************************************************************
 * Copyright (c) 2009 Mukul Gandhi, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Mukul Gandhi - bug 277608 - implementation of xs:short data type
 *     Mukul Gandhi - bug 280798 - PsychoPath XPath 2.0 support for JDK 1.4
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.types;

import java.math.BigInteger;

import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;

public class XSShort extends XSInt {
	
	private static final String XS_SHORT = "xs:short";

	/**
	 * Initializes a representation of 0
	 */
	public XSShort() {
	  this(BigInteger.valueOf(0));
	}
	
	/**
	 * Initializes a representation of the supplied short value
	 * 
	 * @param x
	 *            Short to be stored
	 */
	public XSShort(BigInteger x) {
		super(x);
	}
	
	/**
	 * Retrieves the datatype's full pathname
	 * 
	 * @return "xs:short" which is the datatype's full pathname
	 */
	public String string_type() {
		return XS_SHORT;
	}
	
	/**
	 * Retrieves the datatype's name
	 * 
	 * @return "short" which is the datatype's name
	 */
	public String type_name() {
		return "short";
	}
	
	/**
	 * Creates a new ResultSequence consisting of the extractable 'short' in the
	 * supplied ResultSequence
	 * 
	 * @param arg
	 *            The ResultSequence from which the short is to be extracted
	 * @return New ResultSequence consisting of the 'short' supplied
	 * @throws DynamicError
	 */
	public ResultSequence constructor(ResultSequence arg) throws DynamicError {
		ResultSequence rs = ResultSequenceFactory.create_new();

		if (arg.empty())
			return rs;

		// the function conversion rules apply here too. Get the argument
		// and convert it's string value to a short.
		AnyType aat = arg.first();

		try {
			BigInteger bigInt = new BigInteger(aat.string_value());
			
			// doing the range checking
			BigInteger min = BigInteger.valueOf(-32768L);
			BigInteger max = BigInteger.valueOf(32767L);			

			if (bigInt.compareTo(min) < 0 || bigInt.compareTo(max) > 0) {
			   // invalid input
			   DynamicError.throw_type_error();	
			}
			
			rs.add(new XSShort(bigInt));
			
			return rs;
		} catch (NumberFormatException e) {
			throw DynamicError.cant_cast(null);
		}

	}

}
