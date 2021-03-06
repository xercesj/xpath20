/*******************************************************************************
 * Copyright (c) 2009 Standards for Technology in Automotive Retail and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     David Carver - initial API and implementation
 *     Jesper Steen Moller - bug 283404 - added locale sensitivity test
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.test;

import java.util.Locale;

import org.eclipse.wst.xml.xpath2.processor.internal.types.XPathDecimalFormat;

import junit.framework.TestCase;

public class XPathDecimalFormatTest extends TestCase {

	private static final String DOUBLE_FORMAT = "0.################E0";
	private static final String FLOAT_FORMAT = "0.#######E0";

	public void testDoublePositiveInfinity() {
		XPathDecimalFormat format = new XPathDecimalFormat(DOUBLE_FORMAT);
		Double value = Double.POSITIVE_INFINITY;
		String result = format.xpathFormat(value);
		assertEquals("Unexpected XPath format String:", "INF", result);
	}
	
	public void testDoubleNegativeInfinity() {
		XPathDecimalFormat format = new XPathDecimalFormat(DOUBLE_FORMAT);
		Double value = Double.NEGATIVE_INFINITY;
		String result = format.xpathFormat(value);
		assertEquals("Unexpected XPath format string:", "-INF", result);
	}
	
	public void testFloatPositiveInfinity() {
		XPathDecimalFormat format = new XPathDecimalFormat(FLOAT_FORMAT);
		Float value = Float.POSITIVE_INFINITY;
		String result = format.xpathFormat(value);
		assertEquals("Unexpected XPath format string:", "INF", result);
	}
	
	public void testFloatNegativeInfinity() {
		XPathDecimalFormat format = new XPathDecimalFormat(FLOAT_FORMAT);
		Float value = Float.NEGATIVE_INFINITY;
		String result = format.xpathFormat(value);
		assertEquals("Unexpected XPath format string:", "-INF", result);
	}

	public void testLocaleInsensitivity() {
		Locale.setDefault(Locale.GERMAN);
		XPathDecimalFormat format = new XPathDecimalFormat(FLOAT_FORMAT);
		Float value = Float.valueOf(1.2f);
		String result = format.xpathFormat(value);
		assertEquals("Unexpected XPath format string:", "1.2", result);
	}

}
