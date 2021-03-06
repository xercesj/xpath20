/*******************************************************************************
 * Copyright (c) 2009 Standards for Technology for Automotive Retail and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     David Carver (STAR) - initial API and implementation
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.testsuite.core;

import java.net.URL;
import java.util.Iterator;

import org.apache.xerces.xs.XSModel;
import org.eclipse.wst.xml.xpath2.processor.*;
import org.eclipse.wst.xml.xpath2.processor.ast.XPath;
import org.eclipse.wst.xml.xpath2.processor.internal.types.AnyType;
import org.eclipse.wst.xml.xpath2.processor.test.AbstractPsychoPathTest;
      
      
public class ParenExprTest extends AbstractPsychoPathTest {

   //Arithmetic operations.
   public void test_Parenexpr_1() throws Exception {
      String inputFile = "/TestSources/emptydoc.xml";
      String xqFile = "/Queries/XQuery/Expressions/PrimaryExpr/ParenExpr/Parenexpr-1.xq";
      String resultFile = "/ExpectedTestResults/Expressions/PrimaryExpr/ParenExpr/Parenexpr-1.txt";
      String expectedResult = getExpectedResult(resultFile);
      URL fileURL = bundle.getEntry(inputFile);
      loadDOMDocument(fileURL);
      
      // Get XML Schema Information for the Document
      XSModel schema = getGrammar();

      DynamicContext dc = setupDynamicContext(schema);

      String xpath = extractXPathExpression(xqFile, inputFile);
      String actual = null;
      try {
	   	  XPath path = compileXPath(dc, xpath);
	
	      Evaluator eval = new DefaultEvaluator(dc, domDoc);
	      ResultSequence rs = eval.evaluate(path);
	      	
	      AnyType result = rs.first();
	
	      actual = result.string_value();
      } catch (XPathParserException ex) {
    	 actual = ex.code();
      } catch (StaticError ex) {
         actual = ex.code();
      } catch (DynamicError ex) {
         actual = ex.code();
      }

      assertEquals("XPath Result Error " + xqFile + ":", expectedResult, actual);
        

   }

   //Arithmetic operations.
   public void test_Parenexpr_2() throws Exception {
      String inputFile = "/TestSources/emptydoc.xml";
      String xqFile = "/Queries/XQuery/Expressions/PrimaryExpr/ParenExpr/Parenexpr-2.xq";
      String resultFile = "/ExpectedTestResults/Expressions/PrimaryExpr/ParenExpr/Parenexpr-2.txt";
      String expectedResult = getExpectedResult(resultFile);
      URL fileURL = bundle.getEntry(inputFile);
      loadDOMDocument(fileURL);
      
      // Get XML Schema Information for the Document
      XSModel schema = getGrammar();

      DynamicContext dc = setupDynamicContext(schema);

      String xpath = extractXPathExpression(xqFile, inputFile);
      String actual = null;
      try {
	   	  XPath path = compileXPath(dc, xpath);
	
	      Evaluator eval = new DefaultEvaluator(dc, domDoc);
	      ResultSequence rs = eval.evaluate(path);
	
	      AnyType result = rs.first();
	
	      actual = result.string_value();
      } catch (XPathParserException ex) {
    	 actual = ex.code();
      } catch (StaticError ex) {
         actual = ex.code();
      } catch (DynamicError ex) {
         actual = ex.code();
      }

      assertEquals("XPath Result Error " + xqFile + ":", expectedResult, actual);
        

   }

   //Arithmetic operations.
   public void test_Parenexpr_3() throws Exception {
      String inputFile = "/TestSources/emptydoc.xml";
      String xqFile = "/Queries/XQuery/Expressions/PrimaryExpr/ParenExpr/Parenexpr-3.xq";
      String resultFile = "/ExpectedTestResults/Expressions/PrimaryExpr/ParenExpr/Parenexpr-3.txt";
      String expectedResult = getExpectedResult(resultFile);
      URL fileURL = bundle.getEntry(inputFile);
      loadDOMDocument(fileURL);
      
      // Get XML Schema Information for the Document
      XSModel schema = getGrammar();

      DynamicContext dc = setupDynamicContext(schema);

      String xpath = extractXPathExpression(xqFile, inputFile);
      String actual = null;
      try {
	   	  XPath path = compileXPath(dc, xpath);
	
	      Evaluator eval = new DefaultEvaluator(dc, domDoc);
	      ResultSequence rs = eval.evaluate(path);
	
	      AnyType result = rs.first();
	
	      actual = result.string_value();
      } catch (XPathParserException ex) {
    	 actual = ex.code();
      } catch (StaticError ex) {
         actual = ex.code();
      } catch (DynamicError ex) {
         actual = ex.code();
      }

      assertEquals("XPath Result Error " + xqFile + ":", expectedResult, actual);
        

   }

   //Arithmetic operations.
   public void test_Parenexpr_4() throws Exception {
      String inputFile = "/TestSources/emptydoc.xml";
      String xqFile = "/Queries/XQuery/Expressions/PrimaryExpr/ParenExpr/Parenexpr-4.xq";
      String resultFile = "/ExpectedTestResults/Expressions/PrimaryExpr/ParenExpr/Parenexpr-4.txt";
      String expectedResult = getExpectedResult(resultFile);
      URL fileURL = bundle.getEntry(inputFile);
      loadDOMDocument(fileURL);
      
      // Get XML Schema Information for the Document
      XSModel schema = getGrammar();

      DynamicContext dc = setupDynamicContext(schema);

      String xpath = extractXPathExpression(xqFile, inputFile);
      String actual = null;
      try {
	   	  XPath path = compileXPath(dc, xpath);
	
	      Evaluator eval = new DefaultEvaluator(dc, domDoc);
	      ResultSequence rs = eval.evaluate(path);
	
	      AnyType result = rs.first();
	
	      actual = result.string_value();
      } catch (XPathParserException ex) {
    	 actual = ex.code();
      } catch (StaticError ex) {
         actual = ex.code();
      } catch (DynamicError ex) {
         actual = ex.code();
      }

      assertEquals("XPath Result Error " + xqFile + ":", expectedResult, actual);
        

   }

   //Arithmetic operations.
   public void test_Parenexpr_5() throws Exception {
      String inputFile = "/TestSources/emptydoc.xml";
      String xqFile = "/Queries/XQuery/Expressions/PrimaryExpr/ParenExpr/Parenexpr-5.xq";
      String resultFile = "/ExpectedTestResults/Expressions/PrimaryExpr/ParenExpr/Parenexpr-5.txt";
      String expectedResult = getExpectedResult(resultFile);
      URL fileURL = bundle.getEntry(inputFile);
      loadDOMDocument(fileURL);
      
      // Get XML Schema Information for the Document
      XSModel schema = getGrammar();

      DynamicContext dc = setupDynamicContext(schema);

      String xpath = extractXPathExpression(xqFile, inputFile);
      String actual = null;
      try {
	   	  XPath path = compileXPath(dc, xpath);
	
	      Evaluator eval = new DefaultEvaluator(dc, domDoc);
	      ResultSequence rs = eval.evaluate(path);
	
	      AnyType result = rs.first();
	
	      actual = result.string_value();
      } catch (XPathParserException ex) {
    	 actual = ex.code();
      } catch (StaticError ex) {
         actual = ex.code();
      } catch (DynamicError ex) {
         actual = ex.code();
      }

      assertEquals("XPath Result Error " + xqFile + ":", expectedResult, actual);
        

   }

   //Arithmetic operations.
   public void test_Parenexpr_6() throws Exception {
      String inputFile = "/TestSources/emptydoc.xml";
      String xqFile = "/Queries/XQuery/Expressions/PrimaryExpr/ParenExpr/Parenexpr-6.xq";
      String resultFile = "/ExpectedTestResults/Expressions/PrimaryExpr/ParenExpr/Parenexpr-6.txt";
      String expectedResult = getExpectedResult(resultFile);
      URL fileURL = bundle.getEntry(inputFile);
      loadDOMDocument(fileURL);
      
      // Get XML Schema Information for the Document
      XSModel schema = getGrammar();

      DynamicContext dc = setupDynamicContext(schema);

      String xpath = extractXPathExpression(xqFile, inputFile);
      String actual = null;
      try {
	   	  XPath path = compileXPath(dc, xpath);
	
	      Evaluator eval = new DefaultEvaluator(dc, domDoc);
	      ResultSequence rs = eval.evaluate(path);
	
	      AnyType result = rs.first();
	
	      actual = result.string_value();
      } catch (XPathParserException ex) {
    	 actual = ex.code();
      } catch (StaticError ex) {
         actual = ex.code();
      } catch (DynamicError ex) {
         actual = ex.code();
      }

      assertEquals("XPath Result Error " + xqFile + ":", expectedResult, actual);
        

   }

   //Arithmetic operations.
   public void test_Parenexpr_7() throws Exception {
      String inputFile = "/TestSources/emptydoc.xml";
      String xqFile = "/Queries/XQuery/Expressions/PrimaryExpr/ParenExpr/Parenexpr-7.xq";
      String resultFile = "/ExpectedTestResults/Expressions/PrimaryExpr/ParenExpr/Parenexpr-7.txt";
      String expectedResult = getExpectedResult(resultFile);
      URL fileURL = bundle.getEntry(inputFile);
      loadDOMDocument(fileURL);
      
      // Get XML Schema Information for the Document
      XSModel schema = getGrammar();

      DynamicContext dc = setupDynamicContext(schema);

      String xpath = extractXPathExpression(xqFile, inputFile);
      String actual = null;
      try {
	   	  XPath path = compileXPath(dc, xpath);
	
	      Evaluator eval = new DefaultEvaluator(dc, domDoc);
	      ResultSequence rs = eval.evaluate(path);
	
	      AnyType result = rs.first();
	
	      actual = result.string_value();
      } catch (XPathParserException ex) {
    	 actual = ex.code();
      } catch (StaticError ex) {
         actual = ex.code();
      } catch (DynamicError ex) {
         actual = ex.code();
      }

      assertEquals("XPath Result Error " + xqFile + ":", expectedResult, actual);
        

   }

   //Arithmetic operations.
   public void test_Parenexpr_8() throws Exception {
      String inputFile = "/TestSources/emptydoc.xml";
      String xqFile = "/Queries/XQuery/Expressions/PrimaryExpr/ParenExpr/Parenexpr-8.xq";
      String resultFile = "/ExpectedTestResults/Expressions/PrimaryExpr/ParenExpr/Parenexpr-8.txt";
      String expectedResult = getExpectedResult(resultFile);
      URL fileURL = bundle.getEntry(inputFile);
      loadDOMDocument(fileURL);
      
      // Get XML Schema Information for the Document
      XSModel schema = getGrammar();

      DynamicContext dc = setupDynamicContext(schema);

      String xpath = extractXPathExpression(xqFile, inputFile);
      String actual = null;
      try {
	   	  XPath path = compileXPath(dc, xpath);
	
	      Evaluator eval = new DefaultEvaluator(dc, domDoc);
	      ResultSequence rs = eval.evaluate(path);
	
	      AnyType result = rs.first();
	
	      actual = result.string_value();
      } catch (XPathParserException ex) {
    	 actual = ex.code();
      } catch (StaticError ex) {
         actual = ex.code();
      } catch (DynamicError ex) {
         actual = ex.code();
      }

      assertEquals("XPath Result Error " + xqFile + ":", expectedResult, actual);
        

   }

   //Logical expressions.
   public void test_Parenexpr_9() throws Exception {
      String inputFile = "/TestSources/emptydoc.xml";
      String xqFile = "/Queries/XQuery/Expressions/PrimaryExpr/ParenExpr/Parenexpr-9.xq";
      String resultFile = "/ExpectedTestResults/Expressions/PrimaryExpr/ParenExpr/Parenexpr-9.txt";
      String expectedResult = getExpectedResult(resultFile);
      URL fileURL = bundle.getEntry(inputFile);
      loadDOMDocument(fileURL);
      
      // Get XML Schema Information for the Document
      XSModel schema = getGrammar();

      DynamicContext dc = setupDynamicContext(schema);

      String xpath = extractXPathExpression(xqFile, inputFile);
      String actual = null;
      try {
	   	  XPath path = compileXPath(dc, xpath);
	
	      Evaluator eval = new DefaultEvaluator(dc, domDoc);
	      ResultSequence rs = eval.evaluate(path);
	
	      AnyType result = rs.first();
	
	      actual = result.string_value();
      } catch (XPathParserException ex) {
    	 actual = ex.code();
      } catch (StaticError ex) {
         actual = ex.code();
      } catch (DynamicError ex) {
         actual = ex.code();
      }

      assertEquals("XPath Result Error " + xqFile + ":", expectedResult, actual);
        

   }

   //Logical expressions.
   public void test_Parenexpr_10() throws Exception {
      String inputFile = "/TestSources/emptydoc.xml";
      String xqFile = "/Queries/XQuery/Expressions/PrimaryExpr/ParenExpr/Parenexpr-10.xq";
      String resultFile = "/ExpectedTestResults/Expressions/PrimaryExpr/ParenExpr/Parenexpr-10.txt";
      String expectedResult = getExpectedResult(resultFile);
      URL fileURL = bundle.getEntry(inputFile);
      loadDOMDocument(fileURL);
      
      // Get XML Schema Information for the Document
      XSModel schema = getGrammar();

      DynamicContext dc = setupDynamicContext(schema);

      String xpath = extractXPathExpression(xqFile, inputFile);
      String actual = null;
      try {
	   	  XPath path = compileXPath(dc, xpath);
	
	      Evaluator eval = new DefaultEvaluator(dc, domDoc);
	      ResultSequence rs = eval.evaluate(path);
	
	      AnyType result = rs.first();
	
	      actual = result.string_value();
      } catch (XPathParserException ex) {
    	 actual = ex.code();
      } catch (StaticError ex) {
         actual = ex.code();
      } catch (DynamicError ex) {
         actual = ex.code();
      }

      assertEquals("XPath Result Error " + xqFile + ":", expectedResult, actual);
        

   }

   //Literal.
   public void test_Parenexpr_13() throws Exception {
      String inputFile = "/TestSources/emptydoc.xml";
      String xqFile = "/Queries/XQuery/Expressions/PrimaryExpr/ParenExpr/Parenexpr-13.xq";
      String resultFile = "/ExpectedTestResults/Expressions/PrimaryExpr/ParenExpr/Parenexpr-13.txt";
      String expectedResult = getExpectedResult(resultFile);
      URL fileURL = bundle.getEntry(inputFile);
      loadDOMDocument(fileURL);
      
      // Get XML Schema Information for the Document
      XSModel schema = getGrammar();

      DynamicContext dc = setupDynamicContext(schema);

      String xpath = extractXPathExpression(xqFile, inputFile);
      String actual = null;
      try {
	   	  XPath path = compileXPath(dc, xpath);
	
	      Evaluator eval = new DefaultEvaluator(dc, domDoc);
	      ResultSequence rs = eval.evaluate(path);
	
	      AnyType result = rs.first();
	
	      actual = result.string_value();
      } catch (XPathParserException ex) {
    	 actual = ex.code();
      } catch (StaticError ex) {
         actual = ex.code();
      } catch (DynamicError ex) {
         actual = ex.code();
      }

      assertEquals("XPath Result Error " + xqFile + ":", expectedResult, actual);
        

   }

   //Sequence.
   @SuppressWarnings("unchecked")
public void test_Parenexpr_14() throws Exception {
      String inputFile = "/TestSources/emptydoc.xml";
      String xqFile = "/Queries/XQuery/Expressions/PrimaryExpr/ParenExpr/Parenexpr-14.xq";
      String resultFile = "/ExpectedTestResults/Expressions/PrimaryExpr/ParenExpr/Parenexpr-14.txt";
      String expectedResult = getExpectedResult(resultFile);
      URL fileURL = bundle.getEntry(inputFile);
      loadDOMDocument(fileURL);
      
      // Get XML Schema Information for the Document
      XSModel schema = getGrammar();

      DynamicContext dc = setupDynamicContext(schema);

      String xpath = extractXPathExpression(xqFile, inputFile);
      String actual = null;
      try {
	   	  XPath path = compileXPath(dc, xpath);
	
	      Evaluator eval = new DefaultEvaluator(dc, domDoc);
	      ResultSequence rs = eval.evaluate(path);
	      
	      Iterator iterator = rs.iterator();
	      actual = new String();
	      while (iterator.hasNext()) {
	    	  AnyType result = (AnyType)iterator.next();
	    	  actual = actual + result.string_value() + " ";
	      }

	      actual = actual.trim();
	
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
      