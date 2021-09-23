/*******************************************************************************
 * Copyright (c) 2009 Mukul Gandhi, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Mukul Gandhi - bug 277629 - Initial API and implementation, of xs:unsignedLong
 *                                 data type.
 *     David Carver (STAR) - bug 262765 - fixed abs value tests.
 *     Mukul Gandhi - bug 280798 - PsychoPath support for JDK 1.4
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.types;

import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;

import java.math.BigInteger;

public class XSUnsignedLong extends XSNonNegativeInteger {
	
	private static final String XS_UNSIGNED_LONG = "xs:unsignedLong";

	/**
	 * Initializes a representation of 0
	 */
	public XSUnsignedLong() {
	  this(BigInteger.valueOf(0));
	}
	
	/**
	 * Initializes a representation of the supplied unsignedLong value
	 * 
	 * @param x
	 *            unsignedLong to be stored
	 */
	public XSUnsignedLong(BigInteger x) {
		super(x);
	}
	
	/**
	 * Retrieves the datatype's full pathname
	 * 
	 * @return "xs:unsignedLong" which is the datatype's full pathname
	 */
	public String string_type() {
		return XS_UNSIGNED_LONG;
	}
	
	/**
	 * Retrieves the datatype's name
	 * 
	 * @return "unsignedLong" which is the datatype's name
	 */
	public String type_name() {
		return "unsignedLong";
	}
	
	/**
	 * Creates a new ResultSequence consisting of the extractable unsignedLong
	 * in the supplied ResultSequence
	 * 
	 * @param arg
	 *            The ResultSequence from which the unsignedLong is to be extracted
	 * @return New ResultSequence consisting of the 'unsignedLong' supplied
	 * @throws DynamicError
	 */
	public ResultSequence constructor(ResultSequence arg) throws DynamicError {
		ResultSequence rs = ResultSequenceFactory.create_new();

		if (arg.empty())
			return rs;

		// the function conversion rules apply here too. Get the argument
		// and convert it's string value to a unsignedLong.
		AnyType aat = arg.first();

		try {
			BigInteger bigInt = new BigInteger(aat.string_value());
			
			// doing the range checking
			// min value is 0
			// max value is 18446744073709551615
			BigInteger min = BigInteger.valueOf(0);
			BigInteger max = new BigInteger("18446744073709551615");

			if (bigInt.compareTo(min) < 0 || bigInt.compareTo(max) > 0) {
			   // invalid input
			   throw DynamicError.cant_cast(null);	
			}
			
			rs.add(new XSUnsignedLong(bigInt));
			
			return rs;
		} catch (NumberFormatException e) {
			throw DynamicError.cant_cast(null);
		}

	}

}
