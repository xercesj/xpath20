/*******************************************************************************
 * Copyright (c) 2005, 2009 Andrea Bittau, University College London, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andrea Bittau - initial API and implementation from the PsychoPath XPath 2.0
 *     Mukul Gandhi - bug 273760 - wrong namespace for functions and data types
 *     Mukul Gandhi - bug 274952 - implementation of xs:long data type
 *     Mukul Gandhi - bug 275105 - implementation of xs:int data type
 *     Mukul Gandhi - bug 277599 - implementation of xs:nonPositiveInteger data type
 *     Mukul Gandhi - bug 277602 - implementation of xs:negativeInteger data type
 *     Mukul Gandhi - bug 277608 - implementation of xs:short data type
 *     Mukul Gandhi - bug 277609 - implementation of xs:nonNegativeInteger data type
 *     Mukul Gandhi - bug 277629 - implementation of xs:unsignedLong data type
 *     Mukul Gandhi - bug 277632 - implementation of xs:positiveInteger data type
 *     Mukul Gandhi - bug 277639 - implementation of xs:byte data type
 *     Mukul Gandhi - bug 277642 - implementation of xs:unsignedInt data type
 *     Mukul Gandhi - bug 277645 - implementation of xs:unsighedShort data type
 *     Mukul Gandhi - bug 277650 - implementation of xs:unsignedByte data type
 *     Mukul Gandhi - bug 281046 - implementation of xs:base64Binary data type
 *     David Carver - bug 282223 - implementation of xs:duration data type.
 *     David Carver - bug 262765 - implementation of xs:untypedAtomic data type.
 *     Jesper Moller - bug 297707 - Missing the empty-sequence() type
 *     Mukul Gandhi - bug 309585 - implementation of xs:normalizedString data type
 *     Mukul Gandhi - bug 334478 - implementation of xs:token data type
 *     Mukul Gandhi - bug 334842 - improving support for the data types Name, NCName, ENTITY, 
 *                                 ID, IDREF and NMTOKEN. 
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.function;

import org.eclipse.wst.xml.xpath2.processor.internal.function.ConstructorFL;
import org.eclipse.wst.xml.xpath2.processor.internal.types.AnyAtomicType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.QName;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSAnyURI;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSBase64Binary;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSBoolean;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSByte;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSDate;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSDateTime;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSDayTimeDuration;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSDecimal;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSDouble;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSDuration;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSEntity;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSFloat;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSGDay;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSGMonth;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSGMonthDay;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSGYear;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSGYearMonth;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSHexBinary;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSID;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSIDREF;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSInt;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSInteger;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSLong;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSNCName;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSNMTOKEN;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSName;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSNegativeInteger;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSNonNegativeInteger;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSNonPositiveInteger;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSNormalizedString;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSNotation;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSPositiveInteger;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSShort;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSString;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSTime;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSToken;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSUnsignedByte;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSUnsignedInt;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSUnsignedLong;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSUnsignedShort;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSUntypedAtomic;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSYearMonthDuration;

/**
 * XML Schema control library support.
 */
public class XSCtrLibrary extends ConstructorFL {
	/**
	 * Path to w3.org XML Schema specification.
	 */
	public static final String XML_SCHEMA_NS = "http://www.w3.org/2001/XMLSchema";

	/**
	 * Constructor for XSCtrLibrary.
	 */
	public XSCtrLibrary() {
		super(XML_SCHEMA_NS);

		// add types here
		add_type(new XSString());
		add_type(new XSNormalizedString());
		add_type(new XSToken());
		add_type(new XSName());
		add_type(new XSNCName());
		add_type(new XSNMTOKEN());
		add_type(new XSBoolean());
		add_type(new XSUntypedAtomic());
		add_type(new XSNotation());

		add_abstract_type("anyAtomicType", new AnyAtomicType() {
			public String string_type() {
				return null;
			}
			public String string_value() {
				return null;
			}
		});
		
		// numeric
		add_type(new XSDecimal());
		add_type(new XSFloat());
		add_type(new XSDouble());
		add_type(new XSInteger());
		add_type(new XSLong());
		add_type(new XSInt());
		add_type(new XSNonPositiveInteger());
		add_type(new XSNegativeInteger());
		add_type(new XSShort());
		add_type(new XSNonNegativeInteger());
		add_type(new XSUnsignedLong());
		add_type(new XSPositiveInteger());
		add_type(new XSByte());
		add_type(new XSUnsignedInt());
		add_type(new XSUnsignedShort());
		add_type(new XSUnsignedByte());

		// date
		add_type(new XSDateTime());
		add_type(new XSDate());
		add_type(new XSTime());
		add_type(new XSGYearMonth());
		add_type(new XSGYear());
		add_type(new XSGMonthDay());
		add_type(new XSGMonth());
		add_type(new XSGDay());

		add_type(new QName());
		add_type(new XSAnyURI());
		add_type(new XSYearMonthDuration());
		add_type(new XSDayTimeDuration());
		add_type(new XSDuration());
		add_type(new XSEntity());
		add_type(new XSID());
		add_type(new XSIDREF());
		
		add_type(new XSBase64Binary());
		add_type(new XSHexBinary());
	}

}
