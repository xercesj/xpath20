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
 *     Jesper Steen Moeller - bug 285145 - implement full arity checking
 *     Mukul Gandhi - bug 280798 - PsychoPath support for JDK 1.4
 *     Mukul Gandhi - bug 393904 - improvements to computing typed value of element nodes
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.function;

import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;
import org.eclipse.wst.xml.xpath2.processor.internal.types.AnyAtomicType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.AnyType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.NodeType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.QName;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * fn:data takes a sequence of items and returns a sequence of atomic values.
 * The result of fn:data is the sequence of atomic values produced by applying
 * the following rules to each item in $arg: - If the item is an atomic value,
 * it is returned. - If the item is a node, fn:data() returns the typed value of
 * the node as defined by the accessor function dm:typed-value in Section 5.6
 * typed-value Accessor in the specification.
 */
public class FnData extends Function {
	/**
	 * Constructor for FnData.
	 */
	public FnData() {
		super(new QName("data"), 1);
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

		return atomize(argument);
	}

	/**
	 * Atomize a ResultSequnce argument expression.
	 * 
	 * @param arg
	 *            input expression.
	 * @return Result of operation.
	 */
	public static ResultSequence atomize(ResultSequence arg) throws DynamicError {

		ResultSequence rs = ResultSequenceFactory.create_new();

		for (Iterator i = arg.iterator(); i.hasNext();) {
			AnyType at = (AnyType) i.next();

			if (at instanceof AnyAtomicType) {
				rs.add(at);
			}
			else if (at instanceof NodeType) {
				NodeType nt = (NodeType) at;
				rs.concat(nt.typed_value());
			} else {
				assert false;
			}
		}

		return rs;
	}

	/**
	 * Atomize a ResultSequnce argument expression.
	 * 
	 * @param arg
	 *            input expression.
	 */
	public static void fast_atomize(ResultSequence arg) throws DynamicError {
		for (ListIterator i = arg.iterator(); i.hasNext();) {
			AnyType at = (AnyType) i.next();

			if (at instanceof AnyAtomicType) {
				continue;
			}

			// XXX prolly wrong!
			else if (at instanceof NodeType) {
				NodeType nt = (NodeType) at;

				i.set(nt.typed_value().first());
			} else
				assert false;
		}
	}

	/**
	 * Atomize argument expression of any type.
	 * 
	 * @param arg
	 *            input expression.
	 * @return Result of operation.
	 */
	public static AnyType atomize(AnyType arg) throws DynamicError {
		if (arg instanceof AnyAtomicType)
			return arg;
		else if (arg instanceof NodeType) {
			NodeType nt = (NodeType) arg;

			return nt.typed_value().first();
		} else {
			assert false;
			return null;
		}
	}
}
