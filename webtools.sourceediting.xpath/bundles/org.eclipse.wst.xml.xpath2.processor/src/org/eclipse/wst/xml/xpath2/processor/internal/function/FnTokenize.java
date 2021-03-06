/*******************************************************************************
 * Copyright (c) 2005, 2010 Andrea Bittau, University College London, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andrea Bittau - initial API and implementation from the PsychoPath XPath 2.0 
 *     Jesper Steen Moeller - bug 282096 - clean up string storage
 *     Jesper S Moller      - Bug 281938 - no matches should return full input 
 *     Mukul Gandhi - bug 280798 - PsychoPath XPath 2.0 support for JDK 1.4
 *     Mukul Gandhi - Fixes for XercesJ bug https://issues.apache.org/jira/browse/XERCESJ-1732
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.function;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;
import org.eclipse.wst.xml.xpath2.processor.internal.SeqType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.QName;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSString;
import org.eclipse.wst.xml.xpath2.processor.regex.Matcher;
import org.eclipse.wst.xml.xpath2.processor.regex.PatternSyntaxException;

/**
 * This function breaks the $input string into a sequence of strings, treating
 * any substring that matches $pattern as a separator. The separators themselves
 * are not returned.
 */
public class FnTokenize extends AbstractRegExFunction {
	private static Collection _expected_args = null;

	/**
	 * Constructor for FnTokenize.
	 */
	public FnTokenize() {
		super(new QName("tokenize"), 2, 3);
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
		return tokenize(args);
	}

	/**
	 * Tokenize operation.
	 * 
	 * @param args
	 *            Result from the expressions evaluation.
	 * @throws DynamicError
	 *             Dynamic error.
	 * @return Result of fn:tokenize operation.
	 */
	public static ResultSequence tokenize(Collection args) throws DynamicError {
		Collection cargs = Function.convert_arguments(args, expected_args());

		ResultSequence rs = ResultSequenceFactory.create_new();

		// get args
		Iterator argiter = cargs.iterator();
		ResultSequence arg1 = (ResultSequence) argiter.next();
		String str1 = "";
		if (!arg1.empty()) {
			str1 = ((XSString) arg1.first()).value();
			if (str1.length() == 0) {
			   return rs;	
			}
		}
		else {
			return rs;	
		}

		ResultSequence arg2 = (ResultSequence) argiter.next();
		String pattern = ((XSString) arg2.first()).value();
		String flags = null;

		if (argiter.hasNext()) {
			ResultSequence flagRS = null;
			flagRS = (ResultSequence) argiter.next();
			flags = flagRS.first().string_value();			
			if (!isFlagStrValid(flags)) {
			   throw DynamicError.regex_flags_error(null);	
			}
		}

		try {
			List ret = tokenize(trfPatternStrForSubtraction(pattern), flags, 
					                                             str1);

			for (Iterator retIter = ret.iterator(); retIter.hasNext();) {
			   rs.add(new XSString((String)retIter.next()));	
			}			
		} catch (PatternSyntaxException ex) {
			throw DynamicError.regex_error(ex.getMessage());
		}

		return rs;
	}
	
	private static List tokenize(String pattern, String flags, String src) throws DynamicError {
		
		List tokens = new ArrayList();
		
		Matcher matcher = regex(pattern, flags, src);		
		int startpos = 0;
		int endpos = src.length();
		
		while (matcher.find()) {
			String delim = matcher.group();
			if (delim.length() == 0) {
				throw DynamicError.regex_match_zero_length(null);
			}
			String token = src.substring(startpos, matcher.start());
			startpos = matcher.end();
			tokens.add(token);
		}
		
		if (startpos < endpos) {
			String token = src.substring(startpos, endpos);
			tokens.add(token);
		}
		else if (startpos == endpos) {
			tokens.add("");
		}
		
		return tokens;
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
		}

		return _expected_args;
	}
}
