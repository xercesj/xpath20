/*******************************************************************************
 * Copyright (c) 2009 Mukul Gandhi, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Mukul Gandhi - bug 277650 - Initial API and implementation, of xs:unsignedByte
 *                                 data type.
 *     David Carver (STAR) - bug 262765 - fixed abs value tests.
 *     Mukul Gandhi - bug 280798 - PsychoPath support for JDK 1.4
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.types;

import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;

import java.math.BigInteger;

public class XSUnsignedByte extends XSUnsignedShort {
	
	private static final String XS_UNSIGNED_BYTE = "xs:unsignedByte";

	/**
	 * Initializes a representation of 0
	 */
	public XSUnsignedByte() {
	  this(BigInteger.valueOf(0));
	}
	
	/**
	 * Initializes a representation of the supplied unsignedByte value
	 * 
	 * @param x
	 *            unsignedByte to be stored
	 */
	public XSUnsignedByte(BigInteger x) {
		super(x);
	}
	
	/**
	 * Retrieves the datatype's full pathname
	 * 
	 * @return "xs:unsignedByte" which is the datatype's full pathname
	 */
	public String string_type() {
		return XS_UNSIGNED_BYTE;
	}
	
	/**
	 * Retrieves the datatype's name
	 * 
	 * @return "unsignedByte" which is the datatype's name
	 */
	public String type_name() {
		return "unsignedByte";
	}
	
	/**
	 * Creates a new ResultSequence consisting of the extractable unsignedByte
	 * in the supplied ResultSequence
	 * 
	 * @param arg
	 *            The ResultSequence from which the unsignedByte is to be extracted
	 * @return New ResultSequence consisting of the 'unsignedByte' supplied
	 * @throws DynamicError
	 */
	public ResultSequence constructor(ResultSequence arg) throws DynamicError {
		ResultSequence rs = ResultSequenceFactory.create_new();

		if (arg.empty())
			return rs;

		// the function conversion rules apply here too. Get the argument
		// and convert it's string value to a unsignedByte.
		AnyType aat = arg.first();

		try {
			BigInteger bigInt = new BigInteger(aat.string_value());
			
			// doing the range checking
			// min value is 0
			// max value is 255
			BigInteger min = BigInteger.valueOf(0);
			BigInteger max = BigInteger.valueOf(255L);

			if (bigInt.compareTo(min) < 0 || bigInt.compareTo(max) > 0) {
			   // invalid input
			   throw DynamicError.cant_cast(null);	
			}
			
			rs.add(new XSUnsignedByte(bigInt));
			
			return rs;
		} catch (NumberFormatException e) {
			throw DynamicError.cant_cast(null);
		}

	}

}
