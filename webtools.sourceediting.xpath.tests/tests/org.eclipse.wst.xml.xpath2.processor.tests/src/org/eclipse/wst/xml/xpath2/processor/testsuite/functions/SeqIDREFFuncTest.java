/*******************************************************************************
 * Copyright (c) 2009 Standards for Technology in Automotive Retail and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     David Carver - STAR - initial api and implementation bug 262765 
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.testsuite.functions;

import java.net.URL;

import org.apache.xerces.xs.XSModel;
import org.eclipse.wst.xml.xpath2.processor.*;
import org.eclipse.wst.xml.xpath2.processor.ast.XPath;
import org.eclipse.wst.xml.xpath2.processor.test.AbstractPsychoPathTest;
      
      
public class SeqIDREFFuncTest extends AbstractPsychoPathTest {

   //Evaluation of fn:idref with second argument ommited and no context item.
   public void test_fn_idref_2() throws Exception {
      String inputFile = "/TestSources/emptydoc.xml";
      String xqFile = "/Queries/XQuery/Functions/NodeSeqFunc/SeqIDREFFunc/fn-idref-2.xq";

      String expectedResult = "XPDY0002";
      URL fileURL = bundle.getEntry(inputFile);
      loadDOMDocument(fileURL);
      
      // Get XML Schema Information for the Document
      XSModel schema = getGrammar();

      DynamicContext dc = setupDynamicContext(schema);

      String xpath = "fn:idref(\"argument1\")";
      String actual = null;
      try {
	   	  XPath path = compileXPath(dc, xpath);
	
	      Evaluator eval = new DefaultEvaluator(dc, null); // no context
	      ResultSequence rs = eval.evaluate(path);
         
          actual = buildResultString(rs);
	
      } catch (XPathParserException ex) {
    	 actual = ex.code();
      } catch (StaticError ex) {
         actual = ex.code();
      } catch (DynamicError ex) {
         actual = ex.code();
      }

      assertEquals("XPath Result Error " + xqFile + ":", expectedResult, actual);
        

   }

   //Evaluation of fn:idref with second argument set to "." and no context item.
   public void test_fn_idref_22() throws Exception {
      String inputFile = "/TestSources/emptydoc.xml";
      String xqFile = "/Queries/XQuery/Functions/NodeSeqFunc/SeqIDREFFunc/fn-idref-22.xq";

      String expectedResult = "XPDY0002";
      URL fileURL = bundle.getEntry(inputFile);
      loadDOMDocument(fileURL);
      
      // Get XML Schema Information for the Document
      XSModel schema = getGrammar();

      DynamicContext dc = setupDynamicContext(schema);

      String xpath = "fn:idref(\"argument1\", .)";
      String actual = null;
      try {
	   	  XPath path = compileXPath(dc, xpath);
	
	      Evaluator eval = new DefaultEvaluator(dc, null); // no context
	      ResultSequence rs = eval.evaluate(path);
         
          actual = buildResultString(rs);
	
      } catch (XPathParserException ex) {
    	 actual = ex.code();
      } catch (StaticError ex) {
         actual = ex.code();
      } catch (DynamicError ex) {
         actual = ex.code();
      }

      assertEquals("XPath Result Error " + xqFile + ":", expectedResult, actual);
        

   }

}
      