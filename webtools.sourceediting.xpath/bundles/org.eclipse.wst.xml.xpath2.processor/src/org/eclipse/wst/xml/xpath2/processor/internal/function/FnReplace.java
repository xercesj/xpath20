/*******************************************************************************
 * Copyright (c) 2005, 2010 Andrea Bittau, University College London, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andrea Bittau - initial API and implementation from the PsychoPath XPath 2.0
 *     David Carver (STAR) - bug 262765 - added exception handling to toss correct error numbers. 
 *     Jesper Steen Moeller - bug 285145 - implement full arity checking
 *     Mukul Gandhi - bug 280798 - PsychoPath support for JDK 1.4
 *     Mukul Gandhi - Fixes for XercesJ bug https://issues.apache.org/jira/browse/XERCESJ-1732
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.function;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;
import org.eclipse.wst.xml.xpath2.processor.internal.SeqType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.QName;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSString;
import org.eclipse.wst.xml.xpath2.processor.regex.Matcher;
import org.eclipse.wst.xml.xpath2.processor.regex.PatternSyntaxException;


/**
 * The function returns the xs:string that is obtained by replacing each
 * non-overlapping substring of $input that matches the given $pattern with an
 * occurrence of the $replacement string.
 */
public class FnReplace extends AbstractRegExFunction {
	private static Collection _expected_args = null;

	/**
	 * Constructor for RnReplace.
	 */
	public FnReplace() {
		super(new QName("replace"), 3, 4);
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
		return replace(args);
	}

	/**
	 * Replace operation.
	 * 
	 * @param args
	 *            Result from the expressions evaluation.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of fn:replace operation.
	 */
	public static ResultSequence replace(Collection args) throws DynamicError {
		Collection cargs = Function.convert_arguments(args, expected_args());

		ResultSequence rs = ResultSequenceFactory.create_new();

		// get args
		Iterator argiter = cargs.iterator();
		ResultSequence arg1 = (ResultSequence) argiter.next();
		String str1 = "";
		if (!arg1.empty())
			str1 = ((XSString) arg1.first()).value();

		ResultSequence arg2 = (ResultSequence) argiter.next();
		ResultSequence arg3 = (ResultSequence) argiter.next();
		String flags = null;
		
		if (argiter.hasNext()) {
			ResultSequence flagRS = null;
			flagRS = (ResultSequence) argiter.next();
			flags = flagRS.first().string_value();			
			if (!isFlagStrValid(flags)) {
			   throw DynamicError.regex_flags_error(null);	
			}
		}
		
		String pattern = ((XSString) arg2.first()).value();
		String replacement = ((XSString) arg3.first()).value();
		
		try {
			Matcher matcher = regex(pattern, flags, str1);
			rs.add(new XSString(matcher.replaceAll(replacement)));
			return rs; 
		} catch (PatternSyntaxException ex) {
			throw DynamicError.regex_error(ex.getMessage());
		} catch (IllegalArgumentException ex) {
			throw new DynamicError("FORX0004", ex.getMessage());
		} catch (IndexOutOfBoundsException ex) {
			String className = ex.getClass().getName();
			if (className.endsWith("StringIndexOutOfBoundsException")) {
				throw new DynamicError("FORX0004", ex.getMessage());
			}
			throw new DynamicError("FORX0003", ex.getMessage());
		} catch (Exception ex) {
			throw new DynamicError("FORX0004", ex.getMessage());
		}
	}

	/**
	 * Obtain a list of expected arguments.
	 * 
	 * @return Result of operation.
	 */
	public synchronized static Collection expected_args() {
		if (_expected_args == null) {
			_expected_args = new ArrayList();
			SeqType arg = new SeqType(new XSString(), SeqType.OCC_QMARK);
			_expected_args.add(arg);
			_expected_args.add(new SeqType(new XSString(), SeqType.OCC_NONE));
			_expected_args.add(new SeqType(new XSString(), SeqType.OCC_NONE));
			_expected_args.add(new SeqType(new XSString(), SeqType.OCC_NONE));
		}

		return _expected_args;
	}
}
