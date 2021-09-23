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
 *     David Carver (STAR) - bug 262765 - fixed distinct-values comparison logic.
 *                           There is probably an easier way to do the comparison.
 *     Mukul Gandhi - bug 280798 - PsychoPath support for JDK 1.4
 *     Mukul Gandhi - bug 339025 - fixes to fn:distinct-values function. ability to find distinct values on node items.
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
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSString;

/**
 * Returns the sequence that results from removing from $arg all but one of a
 * set of values that are eq to one other. Values that cannot be compared, i.e.
 * the eq operator is not defined for their types, are considered to be
 * distinct. Values of type xdt:untypedAtomic are compared as if they were of
 * type xs:string. The order in which the sequence of values is returned is
 * implementation dependent.
 */
public class FnDistinctValues extends AbstractCollationEqualFunction {
	/**
	 * Constructor for FnDistinctValues.
	 */
	public FnDistinctValues() {
		super(new QName("distinct-values"), 1, 2);
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
		return distinct_values(args, dynamic_context());
	}

	/**
	 * Distinct-values operation.
	 * 
	 * @param args
	 *            Result from the expressions evaluation.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of fn:distinct-values operation.
	 */
	public static ResultSequence distinct_values(Collection args, DynamicContext context) throws DynamicError {

		ResultSequence rs = ResultSequenceFactory.create_new();

		// get args
		Iterator citer = args.iterator();
		ResultSequence arg1 = (ResultSequence) citer.next();
		ResultSequence arg2 = ResultSequenceFactory.create_new();
		if (citer.hasNext()) {
			arg2 = (ResultSequence) citer.next();
		}
		
		String collationURI = context.default_collation_name();
		if (!arg2.empty()) {
			XSString collation = (XSString) arg2.first();
			collationURI = collation.string_value();
		}

		for (Iterator iter = arg1.iterator(); iter.hasNext();) {
			AnyAtomicType atomizedItem = (AnyAtomicType) FnData.atomize((AnyType) iter.next());
			if (!contains(rs, atomizedItem, context, collationURI))
				rs.add(atomizedItem);
		}

		return rs;
	}
	
	/**
	 * Support for Contains interface.
	 * 
	 * @param rs
	 *            input1 expression sequence.
	 * @param item
	 *            input2 expression of any atomic type.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of operation.
	 */
	protected static boolean contains(ResultSequence rs, AnyAtomicType item,
			DynamicContext context, String collationURI) throws DynamicError {
		/*if (!(item instanceof CmpEq))
			return false; */

		return hasValue(rs, item, context, collationURI);
	}
	
}
