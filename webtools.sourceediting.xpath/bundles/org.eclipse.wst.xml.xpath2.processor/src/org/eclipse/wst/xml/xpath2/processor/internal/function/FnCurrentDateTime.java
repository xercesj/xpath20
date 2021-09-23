/*******************************************************************************
 * Copyright (c) 2005, 2010 Andrea Bittau, University College London, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andrea Bittau - initial API and implementation from the PsychoPath XPath 2.0 
 *     Jesper S Moller - bug 286452 - always return the stable date/time from dynamic context
 *     Mukul Gandhi - bug 280798 - PsychoPath support for JDK 1.4
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.function;

import org.eclipse.wst.xml.xpath2.processor.DynamicContext;
import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;
import org.eclipse.wst.xml.xpath2.processor.internal.types.AnyType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.QName;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSDateTime;

import java.util.Collection;

/**
 * Returns the xs:dateTime (with timezone) from the dynamic context. (See
 * Section C.2 Dynamic Context ComponentsXP.) This is a xs:dateTime that is
 * current at some time during the evaluation of a query or transformation in
 * which fn:current-dateTime() is executed. This function is stable. The precise
 * instant during the query or transformation represented by the value of
 * fn:current-dateTime() is implementation dependent.
 */
public class FnCurrentDateTime extends Function {
	/**
	 * Constructor for FnCurrentDateTime.
	 */
	public FnCurrentDateTime() {
		super(new QName("current-dateTime"), 0);
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
		return current_dateTime(args, dynamic_context());
	}

	/**
	 * Current-Date-Time operation.
	 * 
	 * @param args
	 *            Result from the expressions evaluation.
	 * @param dc
	 *            Result of dynamic context operation.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of fn:current-dateTime operation.
	 */
	public static ResultSequence current_dateTime(Collection args,
			DynamicContext dc) throws DynamicError {
		assert args.size() == 0;

		AnyType res = new XSDateTime(dc.current_date_time(), dc.tz());

		return ResultSequenceFactory.create_new(res);
	}
}
