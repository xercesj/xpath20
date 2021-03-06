/*******************************************************************************
 * Copyright (c) 2005, 2010 Andrea Bittau, University College London, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andrea Bittau - initial API and implementation from the PsychoPath XPath 2.0
 *     David Carver - bug 277774 - XSDEcimal returning wrong values. 
 *     Mukul Gandhi - bug 280798 - PsychoPath XPath 2.0 support for JDK 1.4
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.function;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;
import org.eclipse.wst.xml.xpath2.processor.internal.SeqType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.QName;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSDateTime;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSDecimal;

/**
 * Returns an xs:decimal value between 0 and 60.999..., both inclusive
 * representing the seconds and fractional seconds in the localized value of
 * $arg. Note that the value can be greater than 60 seconds to accommodate
 * occasional leap seconds used to keep human time synchronized with the
 * rotation of the planet. If $arg is the empty sequence, returns the empty
 * sequence.
 */
public class FnSecondsFromDateTime extends Function {
	private static Collection _expected_args = null;

	/**
	 * Constructor for FnSecondsFromDateTime.
	 */
	public FnSecondsFromDateTime() {
		super(new QName("seconds-from-dateTime"), 1);
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
		return seconds_from_date_time(args);
	}

	/**
	 * Seconds-from-dateTime operation.
	 * 
	 * @param args
	 *            Result from the expressions evaluation.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of fn:seconds-from-dateTime operation.
	 */
	public static ResultSequence seconds_from_date_time(Collection args)
			throws DynamicError {
		Collection cargs = Function.convert_arguments(args, expected_args());

		ResultSequence arg1 = (ResultSequence) cargs.iterator().next();

		ResultSequence rs = ResultSequenceFactory.create_new();

		if (arg1.empty()) {
			return rs;
		}

		XSDateTime dt = (XSDateTime) arg1.first();

		double res = dt.second();

		rs.add(new XSDecimal(new BigDecimal(res)));

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
			_expected_args
					.add(new SeqType(new XSDateTime(), SeqType.OCC_QMARK));
		}

		return _expected_args;
	}
}
