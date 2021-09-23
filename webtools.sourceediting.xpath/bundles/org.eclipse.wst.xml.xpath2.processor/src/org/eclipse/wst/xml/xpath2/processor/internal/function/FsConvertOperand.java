/*******************************************************************************
 * Copyright (c) 2005, 2010 Andrea Bittau, University College London, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andrea Bittau - initial API and implementation from the PsychoPath XPath 2.0 
 *     Mukul Gandhi - bug 280798 - PsychoPath support for JDK 1.4
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.function;

import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;
import org.eclipse.wst.xml.xpath2.processor.internal.types.AnyAtomicType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.AnyType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.CtrType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.NumericType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.QName;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSDouble;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSString;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSUntypedAtomic;

import java.util.Collection;
import java.util.Iterator;

/**
 * Function to convert a sequence of items to a sequence of atomic values.
 */
public class FsConvertOperand extends Function {

	public FsConvertOperand() {
		super(new QName("convert-operand"), 2);
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
		return convert_operand(args);
	}

	/**
	 * Convert-Operand operation.
	 * 
	 * @param args
	 *            Result from the expressions evaluation.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of fs: operation.
	 */
	public static ResultSequence convert_operand(Collection args)
			throws DynamicError {

		assert args.size() == 2;

		Iterator iter = args.iterator();

		ResultSequence actual = (ResultSequence) iter.next();
		ResultSequence expected = (ResultSequence) iter.next();

		if (expected.size() != 1)
			DynamicError.throw_type_error();

		AnyType at = expected.first();

		if (!(at instanceof AnyAtomicType))
			DynamicError.throw_type_error();

		AnyAtomicType exp_aat = (AnyAtomicType) at;

		ResultSequence result = ResultSequenceFactory.create_new();

		// 1
		if (actual.empty())
			return result;

		// convert sequence
		for (Iterator i = actual.iterator(); i.hasNext();) {
			AnyType item = (AnyType) i.next();

			// 2
			if (item instanceof XSUntypedAtomic) {
				// a
				if (exp_aat instanceof XSUntypedAtomic)
					result.add(new XSString(item.string_value()));
				// b
				else if (exp_aat instanceof NumericType)
					result.add(new XSDouble(item.string_value()));
				// c
				else {
					assert exp_aat instanceof CtrType;

					CtrType cons = (CtrType) exp_aat;

					ResultSequence tmp = ResultSequenceFactory
							.create_new(new XSString(item.string_value()));

					ResultSequence converted = cons.constructor(tmp);
					result.concat(converted);

					tmp.release();
				}
			}
			// 4
			else
				result.add(item);

		}

		return result;
	}
}
