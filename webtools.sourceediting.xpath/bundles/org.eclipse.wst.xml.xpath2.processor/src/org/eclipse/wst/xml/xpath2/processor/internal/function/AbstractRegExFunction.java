/*******************************************************************************
 * Copyright (c) 2009, 2010 Standards for Technology in Automotive Retail and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     David Carver - bug 262765 - initial API and implementation
 *     Mukul Gandhi - bug 280798 - PsychoPath XPath 2.0 support for JDK 1.4
 *     Mukul Gandhi - Fixes for XercesJ bug https://issues.apache.org/jira/browse/XERCESJ-1732
 *******************************************************************************/
package org.eclipse.wst.xml.xpath2.processor.internal.function;

import org.eclipse.wst.xml.xpath2.processor.internal.types.QName;
import org.eclipse.wst.xml.xpath2.processor.regex.Matcher;
import org.eclipse.wst.xml.xpath2.processor.regex.Pattern;


public abstract class AbstractRegExFunction extends Function {
	private static final String validflags = "smix";

	public AbstractRegExFunction(QName name, int arity) {
		super(name, arity);
	}
	
	public AbstractRegExFunction(QName name, int min_arity, int max_arity) {
		super(name, min_arity, max_arity);
	}
	
	/*
	 * Transform input pattern string, to resolve differences between, 
	 * XSD regex subtraction operator and java regex subtraction operator. 
	 */
	protected static String trfPatternStrForSubtraction(String pattern) {
		String transformedPatternStr = pattern;
		
		int indx1 = transformedPatternStr.indexOf("-[");
		if (indx1 != -1) {
			String subsPrev = transformedPatternStr.substring(0, indx1);
			String subsAfter = transformedPatternStr.substring(indx1 + 2);
			if ((subsPrev.indexOf("[") != -1) && (subsAfter.indexOf("]]") != -1)) {
				transformedPatternStr = transformedPatternStr.replaceAll("\\-\\[", 
						                                                    "&&[^");	
			}
		}
		
		return transformedPatternStr;
	}
	
	protected static Matcher regex(String pattern, String flags, String src) {
		Matcher matcher = compileAndExecute(pattern, flags, src);
		return matcher;
	}
	
	protected static boolean isFlagStrValid(String flags) {
       boolean flagStrValid = true;
       
       if (flags.length() > 0) {
    	  for (int idx = 0; idx < flags.length(); idx++) {
    		 if (validflags.indexOf(flags.charAt(idx)) == -1) {
    			flagStrValid = false;
    			break;
    		 }
    	  }
       }
       
       return flagStrValid; 
	}
	
	private static Matcher compileAndExecute(String pattern, String flags, 
			                                 String src) {
		int flag = Pattern.UNIX_LINES;
		if (flags != null) {
			if (flags.indexOf("m") >= 0) {
				flag = flag | Pattern.MULTILINE;
			}
			if (flags.indexOf("s") >= 0) {
				flag = flag | Pattern.DOTALL;
			}
			if (flags.indexOf("i") >= 0) {
				flag = flag | Pattern.CASE_INSENSITIVE;
			}
			
			if (flags.indexOf("x") >= 0) {
				flag = flag | Pattern.IGNORE_WHITESPACE;
			}
		}
		
		Pattern p = Pattern.compile(pattern, flag);
		return p.matcher(src);
	}

}
