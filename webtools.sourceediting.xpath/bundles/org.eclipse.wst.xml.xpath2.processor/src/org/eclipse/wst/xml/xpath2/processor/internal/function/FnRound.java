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
 *     Mukul Gandhi - bug 280798 - PsychoPath support for JDK 1.4
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.function;

import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;
import org.eclipse.wst.xml.xpath2.processor.internal.types.NumericType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.QName;

import java.util.Collection;

/**
 * Returns the number with no fractional part that is closest to the argument.
 * If there are two such numbers, then the one that is closest to positive
 * infinity is returned. More formally, fn:round(x) produces the same result as
 * fn:floor(x+0.5). If type of $arg is one of the four numeric types xs:float,
 * xs:double, xs:decimal or xs:integer the type of the return is the same as the
 * type of $arg. If the type of $arg is a type derived from one of the numeric
 * types, the type of the return is the base numeric type.
 */
public class FnRound extends Function {
	/**
	 * Constructor for FnRound.
	 */
	public FnRound() {
		super(new QName("round"), 1);
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
		// 1 argument only!
		assert args.size() >= min_arity() && args.size() <= max_arity();

		ResultSequence argument = (ResultSequence) args.iterator().next();

		return fn_round(argument);
	}

	/**
	 * Round operation.
	 * 
	 * @param arg
	 *            Result from the expressions evaluation.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of fn:round operation.
	 */
	public static ResultSequence fn_round(ResultSequence arg)
			throws DynamicError {
		ResultSequence rs = ResultSequenceFactory.create_new();

		// sanity chex
		NumericType nt = FnAbs.get_single_numeric_arg(arg);

		// empty arg
		if (nt == null)
			return rs;

		rs.add(nt.round());
		return rs;
	}
}
