/*******************************************************************************
 * Copyright (c) 2010 Mukul Gandhi, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Mukul Gandhi - bug 309585 - implementation of xs:normalizedString data type
 *     Mukul Gandhi - bug 334478 - refactoring code to cater to xs:token data type
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.types;

import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;

/**
 * A representation of the xs:normalizedString datatype
 */
public class XSNormalizedString extends XSString {

	private static final String XS_NORMALIZEDSTRING = "xs:normalizedString";

	/**
	 * Initialises using the supplied String
	 * 
	 * @param x
	 *            The String to initialise to
	 */
	public XSNormalizedString(String x) {
		super(x);
	}

	/**
	 * Initialises to null
	 */
	public XSNormalizedString() {
		this(null);
	}

	/**
	 * Retrieves the datatype's full pathname
	 * 
	 * @return "xs:normalizedString" which is the datatype's full pathname
	 */
	public String string_type() {
		return XS_NORMALIZEDSTRING;
	}

	/**
	 * Retrieves the datatype's name
	 * 
	 * @return "normalizedString" which is the datatype's name
	 */
	public String type_name() {
		return "normalizedString";
	}

	/**
	 * Creates a new ResultSequence consisting of the extractable String in the
	 * supplied ResultSequence
	 * 
	 * @param arg
	 *            The ResultSequence from which to extract the String
	 * @return New ResultSequence consisting of the supplied String
	 * @throws DynamicError
	 */
	public ResultSequence constructor(ResultSequence arg) throws DynamicError {
		ResultSequence rs = ResultSequenceFactory.create_new();

		if (arg.empty())
		   return rs;

		AnyType aat = arg.first();

		String srcString = aat.string_value();
		if (!isSatisfiesConstraints(srcString)) {
			// invalid input
			DynamicError.throw_type_error();
		}
		else {
			rs.add(new XSNormalizedString(srcString));	
		}

		return rs;
	}
	
	/*
	 * Does the string in context satisfies constraints of the datatype, xs:normalizedString. 
	 */
	protected boolean isSatisfiesConstraints(String srcString) {
	   
		boolean isNormalizedStr = true;
		
		// the xs:normalizedString value cannot contain, 'carriage return', 'line feed' and 'tab' characters.
		if ((srcString.indexOf("\r") != -1) || (srcString.indexOf("\n") != -1) || (srcString.indexOf("\t") != -1)) {
			// invalid input
			isNormalizedStr = false;
		}
		
		return isNormalizedStr;
		  
	} // isSatisfiesConstraints

}
