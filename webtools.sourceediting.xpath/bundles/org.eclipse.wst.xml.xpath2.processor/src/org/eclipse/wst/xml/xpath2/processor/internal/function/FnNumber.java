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
 *     Jesper Steen Moeller - bug 262765 - fixes float handling for fn:number 
 *     Mukul Gandhi - bug 298519 - improvements to fn:number implementation,
 *                                 catering to node arguments. 
 *     Mukul Gandhi - bug 280798 - PsychoPath XPath 2.0 support for JDK 1.4
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.function;

import java.util.Collection;

import org.eclipse.wst.xml.xpath2.processor.DynamicContext;
import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;
import org.eclipse.wst.xml.xpath2.processor.internal.TypeError;
import org.eclipse.wst.xml.xpath2.processor.internal.types.AnyAtomicType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.AnyType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.NodeType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.QName;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSDouble;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSFloat;

/**
 * Returns the value indicated by $arg or, if $arg is not specified, the context
 * item after atomization, converted to an xs:double. If $arg or the context
 * item cannot be converted to an xs:double, the xs:double value NaN is
 * returned. If the context item is undefined an error is raised:
 * [err:FONC0001].
 */
public class FnNumber extends Function {
	/**
	 * Constructor for FnNumber.
	 */
	public FnNumber() {
		super(new QName("number"), 0, 1);
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

		assert args.size() >= min_arity() && args.size() <= max_arity();

		ResultSequence argument = null;
		if (args.isEmpty()) {
			argument = getResultSetForArityZero(dynamic_context());
		} else {
			argument = (ResultSequence) args.iterator().next();
		}

		ResultSequence rs = ResultSequenceFactory.create_new();
		rs.add(fn_number(argument, dynamic_context()));
		return rs;
	}

	/**
	 * Number operation.
	 * 
	 * @param arg
	 *            Result from the expressions evaluation.
	 * @param dc
	 *            Result of dynamic context operation.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of fn:number operation.
	 */
	public static XSDouble fn_number(ResultSequence arg, DynamicContext dc)
			throws DynamicError {

		if (arg.size() > 1) {
			throw new DynamicError(TypeError.invalid_type("bad argument passed to fn:number()"));
		} else if (arg.size() == 1) {
			AnyType at = arg.first();

			/*
			if (!(at instanceof AnyAtomicType))
				DynamicError.throw_type_error();
			*/
			
			if (at instanceof AnyAtomicType) {
			  if ((at instanceof XSDouble)) {
				 return (XSDouble)at;
			  } else if ((at instanceof XSFloat)) {
				  float value = ((XSFloat)at).float_value();
				  if (Float.isNaN(value)) {
					  return new XSDouble(Double.NaN);
				  } else if (value == Float.NEGATIVE_INFINITY) {
					  return new XSDouble(Double.NEGATIVE_INFINITY);
				  } else if (value == Float.POSITIVE_INFINITY) {
					  return new XSDouble(Double.POSITIVE_INFINITY);
				  } else {
					  return new XSDouble((double)value); 
				  }
			  } else {
				 XSDouble d = XSDouble.parse_double(at.string_value());
				 return d != null ? d : new XSDouble(Double.NaN);
			  }
			}
			else if (at instanceof NodeType) {
				XSDouble d = XSDouble.parse_double((FnData.atomize(at)).string_value());
				return d != null ? d : new XSDouble(Double.NaN);
			}
		} else {
			return new XSDouble(Double.NaN);
		}
		
		// unreach
		return null;
	}

}
