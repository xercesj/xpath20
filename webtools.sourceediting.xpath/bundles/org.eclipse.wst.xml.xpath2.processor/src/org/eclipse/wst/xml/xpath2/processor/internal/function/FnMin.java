/*******************************************************************************
 * Copyright (c) 2005, 2010 Andrea Bittau, University College London, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andrea Bittau - initial API and implementation from the PsychoPath XPath 2.0 
 *     Jesper Moller - bug 280555 - Add pluggable collation support
 *     David Carver (STAR) - bug 262765 - fixed promotion issue 
 *     Jesper Moller - bug 281028 - fix promotion rules for fn:min
 *     Mukul Gandhi - bug 280798 - PsychoPath XPath 2.0 support for JDK 1.4
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.function;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.wst.xml.xpath2.processor.DynamicContext;
import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;
import org.eclipse.wst.xml.xpath2.processor.internal.types.AnyAtomicType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.AnyType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.QName;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSDouble;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSFloat;
import org.eclipse.wst.xml.xpath2.processor.internal.utils.ComparableTypePromoter;
import org.eclipse.wst.xml.xpath2.processor.internal.utils.TypePromoter;

/**
 * selects an item from the input sequence $arg whose value is less than or
 * equal to the value of every other item in the input sequence. If there are
 * two or more such items, then the specific item whose value is returned is
 * implementation independent.
 */
public class FnMin extends Function {
	/**
	 * Constructor for FnMin.
	 */
	public FnMin() {
		super(new QName("min"), 1);
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
		return min(args, dynamic_context());
	}

	/**
	 * Min operation.
	 * 
	 * @param args
	 *            Result from the expressions evaluation.
	 * @param dynamic 
	 *            Dynamic context
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of fn:min operation.
	 */
	public static ResultSequence min(Collection args, DynamicContext context) throws DynamicError {

		ResultSequence arg = FnMax.get_arg(args, CmpLt.class);
		if (arg.empty())
			return ResultSequenceFactory.create_new();

		CmpLt min = null;
		TypePromoter tp = new ComparableTypePromoter();
		tp.considerSequence(arg);

		for (Iterator iter = arg.iterator(); iter.hasNext();) {
			AnyAtomicType conv = convertInputItem(tp, (AnyType) iter.next());
			
			if (conv instanceof XSDouble && ((XSDouble)conv).nan() || conv instanceof XSFloat && ((XSFloat)conv).nan()) {
				return ResultSequenceFactory.create_new(tp.promote(new XSFloat(Float.NaN)));
			}
			if (min == null || ((CmpLt)conv).lt((AnyType)min, context)) {
				min = (CmpLt)conv;
			}
		}
		return ResultSequenceFactory.create_new((AnyType) min);
	}

}
