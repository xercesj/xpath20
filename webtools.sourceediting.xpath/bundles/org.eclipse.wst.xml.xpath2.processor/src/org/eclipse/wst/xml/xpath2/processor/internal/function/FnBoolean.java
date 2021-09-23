/*******************************************************************************
 * Copyright (c) 2005, 2010 Andrea Bittau, University College London, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andrea Bittau - initial API and implementation from the PsychoPath XPath 2.0 
 *     Jesper Steen Moeller - bug 285145 - implement full arity checking
 *     Jesper Steen Moller  - bug 262765 - use correct 'effective boolean value'
 *     David Carver (STAR) - bug 262765 - fix checking of data types.
 *     Mukul Gandhi - bug 280798 - PsychoPath support for JDK 1.4
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.function;

import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;
import org.eclipse.wst.xml.xpath2.processor.internal.types.AnyAtomicType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.AnyType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.CalendarType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.NodeType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.NumericType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.QName;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSBoolean;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSDouble;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSFloat;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSString;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSUntypedAtomic;

import java.util.Collection;

/**
 * Computes the effective boolean value of the sequence $arg. If $arg is the
 * empty sequence, returns false. If $arg contains a single atomic value, then
 * the function returns false if $arg is: - The singleton xs:boolean value
 * false. - The singleton value "" (zero-length string) of type xs:string or
 * xdt:untypedAtomic. - A singleton numeric value that is numerically equal to
 * zero. - The singleton xs:float or xs:double value NaN. In all other cases,
 * returns true.
 */
public class FnBoolean extends Function {
	/**
	 * Constructor for FnBoolean.
	 */
	public FnBoolean() {
		super(new QName("boolean"), 1);
	}

	/**
	 * Evaluate arguments.
	 * 
	 * @param args
	 *            argument expressions.
	 * @return Result of evaluation.
	 */
	public ResultSequence evaluate(Collection args) throws DynamicError {
		// 1 argument only!
		assert args.size() >= min_arity() && args.size() <= max_arity();

		ResultSequence argument = (ResultSequence) args.iterator().next();

		return ResultSequenceFactory.create_new(fn_boolean(argument));
	}

	private static final XSBoolean TRUE = new XSBoolean(true);
	
	private static final XSBoolean FALSE = new XSBoolean(false);
	
	/**
	 * Boolean operation.
	 * 
	 * @param arg
	 *            Result from the expressions evaluation.
	 * @return Result of fn:boolean operation.
	 * @throws DynamicError 
	 */
	public static XSBoolean fn_boolean(ResultSequence arg) throws DynamicError {
		if (arg.empty())
			return FALSE;

		AnyType at = arg.first();
		
		if (at instanceof CalendarType) {
			throw DynamicError.throw_type_error();
		}

		
		if (at instanceof NodeType)
			return TRUE;
		
		if (arg.size() > 1)
			throw DynamicError.throw_type_error();

		// XXX ??
		if (!(at instanceof AnyAtomicType))
			return TRUE;

		// ok we got 1 single atomic type element

		if (at instanceof XSBoolean) {
			if (!((XSBoolean) at).value())
				return FALSE;
		}

		if ((at instanceof XSString) || (at instanceof XSUntypedAtomic)) {
			if (at.string_value().equals(""))
				return FALSE;
		}

		if (at instanceof NumericType) {
			if (((NumericType) at).zero())
				return FALSE;
		}

		if ((at instanceof XSFloat) && (((XSFloat) at).nan()))
			return FALSE;

		if ((at instanceof XSDouble) && (((XSDouble) at).nan()))
			return FALSE;
		

		return TRUE;
	}

}
