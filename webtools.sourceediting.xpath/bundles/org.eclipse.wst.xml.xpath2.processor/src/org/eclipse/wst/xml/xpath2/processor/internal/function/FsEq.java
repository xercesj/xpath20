/*******************************************************************************
 * Copyright (c) 2005, 2010 Andrea Bittau, University College London, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andrea Bittau - initial API and implementation from the PsychoPath XPath 2.0
 *     Mukul Gandhi - bug 276134 - improvements to schema aware primitive type support
 *                                 for attribute/element nodes 
 *     David Carver - bug 262765 - fixed comparison on sequence range values.
 *     Jesper S Moller - bug 283214 - fix eq for untyped atomic values
 *     Jesper Steen Moeller - bug 285145 - implement full arity checking
 *     Jesper Steen Moeller - bug 280555 - Add pluggable collation support
 *     Mukul Gandhi - bug 280798 - PsychoPath support for JDK 1.4
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.function;

import org.eclipse.wst.xml.xpath2.processor.DynamicContext;
import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;
import org.eclipse.wst.xml.xpath2.processor.internal.TypeError;
import org.eclipse.wst.xml.xpath2.processor.internal.types.AnyType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.NumericType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.QName;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSBoolean;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSDouble;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSString;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSUntypedAtomic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Class for the Equality function.
 */
public class FsEq extends Function {
	/**
	 * Constructor for FsEq.
	 */
	public FsEq() {
		super(new QName("eq"), 2);
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

		return fs_eq_value(args, dynamic_context());
	}

	/**
	 * Converts arguments to values.
	 * 
	 * @param args
	 *            Result from expressions evaluation.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of conversion.
	 */
	private static Collection value_convert_args(Collection args)
			throws DynamicError {
		Collection result = new ArrayList(args.size());

		// atomize arguments
		for (Iterator i = args.iterator(); i.hasNext();) {
			ResultSequence rs = (ResultSequence) i.next();

			//FnData.fast_atomize(rs);
			rs = FnData.atomize(rs);

			if (rs.empty())
				return new ArrayList();

			if (rs.size() > 1)
				throw new DynamicError(TypeError.invalid_type(null));

			AnyType arg = rs.first();

			if (arg instanceof XSUntypedAtomic)
				arg = new XSString(arg.string_value());

			rs = ResultSequenceFactory.create_new();
			rs.add(arg);
			result.add(rs);
		}

		return result;
	}

	/**
	 * Conversion operation for the values of the arguments.
	 * 
	 * @param args
	 *            Result from convert value operation.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of conversion.
	 */
	public static ResultSequence fs_eq_value(Collection args, DynamicContext dynamicContext)
			throws DynamicError {
		return do_cmp_value_op(args, CmpEq.class, "eq", dynamicContext);
	}

	/**
	 * A fast Equality operation, no conversion for the inputs performed.
	 * 
	 * @param one
	 *            input1 of any type.
	 * @param two
	 *            input2 of any type.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of Equality operation.
	 */
	public static boolean fs_eq_fast(AnyType one, AnyType two, DynamicContext dynamicContext)
			throws DynamicError {

		one = FnData.atomize(one);
		two = FnData.atomize(two);

		if (one instanceof XSUntypedAtomic)
			one = new XSString(one.string_value());

		if (two instanceof XSUntypedAtomic)
			two = new XSString(two.string_value());

		if (!(one instanceof CmpEq))
			DynamicError.throw_type_error();

		CmpEq cmpone = (CmpEq) one;

		return cmpone.eq(two, dynamicContext);
	}

	/**
	 * Making sure that the types are the same before comparing the inputs.
	 * 
	 * @param a
	 *            input1 of any type.
	 * @param b
	 *            input2 of any type.
	 * @param dc
	 *              Dynamic Context
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of Equality operation.
	 */
	private static boolean do_general_pair(AnyType a, AnyType b,
			Method comparator, DynamicContext dc) throws DynamicError {

		// section 3.5.2

		// rule a
		// if one is untyped and other is numeric, cast untyped to
		// double
		if ((a instanceof XSUntypedAtomic && b instanceof NumericType)
				|| (b instanceof XSUntypedAtomic && a instanceof NumericType)) {
			if (a instanceof XSUntypedAtomic)
				a = new XSDouble(a.string_value());
			else
				b = new XSDouble(b.string_value());

		}

		// rule b
		// if one is untyped and other is string or untyped, then cast
		// untyped to string
		else if ((a instanceof XSUntypedAtomic
				&& (b instanceof XSString || b instanceof XSUntypedAtomic) || (b instanceof XSUntypedAtomic && (a instanceof XSString || a instanceof XSUntypedAtomic)))) {

			if (a instanceof XSUntypedAtomic)
				a = new XSString(a.string_value());
			if (b instanceof XSUntypedAtomic)
				b = new XSString(b.string_value());
		}

		// rule c
		// if one is untyped and other is not string,untyped,numeric
		// cast untyped to dynamic type of other

		// XXX?
		else if (a instanceof XSUntypedAtomic) {
			ResultSequence converted = ResultSequenceFactory.create_new(a);
			assert converted.size() == 1;
			a = converted.first();
		} else if (b instanceof XSUntypedAtomic) {
			ResultSequence converted = ResultSequenceFactory.create_new(b);
			assert converted.size() == 1;
			b = converted.first();
		}

		// rule d
		// if value comparison is true, return true.

		ResultSequence one = ResultSequenceFactory.create_new(a);
		ResultSequence two = ResultSequenceFactory.create_new(b);

		Collection args = new ArrayList();
		args.add(one);
		args.add(two);

		Object margs[] = { args, dc };

		ResultSequence result = null;
		try {
			result = (ResultSequence) comparator.invoke(null, margs);
		} catch (IllegalAccessException err) {
			assert false;
		} catch (InvocationTargetException err) {
			Throwable ex = err.getTargetException();

			if (ex instanceof DynamicError)
				throw (DynamicError) ex;

		}

		if (((XSBoolean) result.first()).value())
			return true;

		return false;
	}

	/**
	 * A general equality function.
	 * 
	 * @param args
	 *            input arguments.
	 * @param dc
	 *         Dynamic context 
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of general equality operation.
	 */
	public static ResultSequence fs_eq_general(Collection args, DynamicContext dc)
			throws DynamicError {
		return do_cmp_general_op(args, FsEq.class, "fs_eq_value", dc);
	}

	// voodoo 3
	/**
	 * Actual equality operation for fs_eq_general.
	 * 
	 * @param args
	 *            input arguments.
	 * @param type
	 *            type of the arguments.
	 * @param mname
	 *            Method name for template simulation.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of the operation.
	 */
	public static ResultSequence do_cmp_general_op(Collection args, Class type,
			String mname, DynamicContext dc) throws DynamicError {

		// do the voodoo
		Method comparator = null;

		try {
			Class margsdef[] = { Collection.class, DynamicContext.class };

			comparator = type.getMethod(mname, margsdef);

		} catch (NoSuchMethodException err) {
			throw new RuntimeException("Can�'t find method : " + mname, err);
		}

		// sanity check args and get them
		if (args.size() != 2)
			DynamicError.throw_type_error();

		Iterator argiter = args.iterator();

		ResultSequence one = (ResultSequence) argiter.next();
		ResultSequence two = (ResultSequence) argiter.next();

		// XXX ?
		if (one.empty() || two.empty())
			return ResultSequenceFactory.create_new(new XSBoolean(false));

		// atomize
		one = FnData.atomize(one);
		two = FnData.atomize(two);

		// we gotta find a pair that satisfied the condition
		for (Iterator i = one.iterator(); i.hasNext();) {
			AnyType a = (AnyType) i.next();
			for (Iterator j = two.iterator(); j.hasNext();) {
				AnyType b = (AnyType) j.next();

				if (do_general_pair(a, b, comparator, dc))
					return ResultSequenceFactory
							.create_new(new XSBoolean(true));
			}
		}

		return ResultSequenceFactory.create_new(new XSBoolean(false));
	}

	// voodoo 2
	/**
	 * Actual equality operation for fs_eq_value.
	 * 
	 * @param args
	 *            input arguments.
	 * @param type
	 *            type of the arguments.
	 * @param mname
	 *            Method name for template simulation.
	 * @param dynamicContext 
	 *             Dynamic error.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of the operation.
	 */
	public static ResultSequence do_cmp_value_op(Collection args, Class type,
			String mname, DynamicContext dynamicContext) throws DynamicError {

		// sanity check args + convert em
		if (args.size() != 2)
			DynamicError.throw_type_error();

		Collection cargs = value_convert_args(args);

		ResultSequence result = ResultSequenceFactory.create_new();

		if (cargs.size() == 0)
			return result;

		// make sure arugments are comparable by equality
		Iterator argi = cargs.iterator();
		AnyType arg = ((ResultSequence) argi.next()).first();
		ResultSequence arg2 = (ResultSequence) argi.next();

		if (arg2.size() != 1)
			DynamicError.throw_type_error();

		if (!(type.isInstance(arg)))
			DynamicError.throw_type_error();

		try {
			Class margsdef[] = { AnyType.class, DynamicContext.class };
			Method method = null;

			method = type.getMethod(mname, margsdef);

			Object margs[] = { arg2.first(), dynamicContext };
			Boolean cmpres = (Boolean) method.invoke(arg, margs);

			return ResultSequenceFactory.create_new(new XSBoolean(cmpres
					.booleanValue()));
		} catch (NoSuchMethodException err) {
			assert false;
			throw new RuntimeException("cannot compare using method " + mname, err);
		} catch (IllegalAccessException err) {
			assert false;
			throw new RuntimeException("cannot compare using method " + mname, err);
		} catch (InvocationTargetException err) {
			Throwable ex = err.getTargetException();

			if (ex instanceof DynamicError)
				throw (DynamicError) ex;

			throw new RuntimeException("cannot compare using method " + mname, ex);
		}
	}
}
