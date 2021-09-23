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

package org.eclipse.wst.xml.xpath2.processor.testsuite.schema;

import java.net.URL;

import org.apache.xerces.xs.XSModel;
import org.eclipse.wst.xml.xpath2.processor.*;
import org.eclipse.wst.xml.xpath2.processor.ast.XPath;
import org.eclipse.wst.xml.xpath2.processor.test.AbstractPsychoPathTest;
      
      
public class CatalogTest extends AbstractPsychoPathTest {

   //Check variable name is used, if source is not "emptydoc".
   public void test_Catalog001() throws Exception {
      String inputFile = "/XQTSCatalog.xml";
      String xqFile = "/Queries/XQuery/Catalog/Catalog001.xq";
      String resultFile = "/ExpectedTestResults/Catalog/Catalog001.xml";
      String expectedResult = getExpectedResult(resultFile);
      URL fileURL = bundle.getEntry(inputFile);
      loadDOMDocument(fileURL);
      
      // Get XML Schema Information for the Document
      XSModel schema = getGrammar();

      DynamicContext dc = setupDynamicContext(schema);

      String xpath = "for $x in $input-context//*:input-file[count(@variable) = 0 and ./text() != \"emptydoc\"] return string($x/../@name)";
      String actual = null;
      try {
	   	  XPath path = compileXPath(dc, xpath);
	
	      Evaluator eval = new DefaultEvaluator(dc, domDoc);
	      ResultSequence rs = eval.evaluate(path);
         
          actual = "<missing-variable>" + buildXMLResultString(rs) + "</missing-variable>";
	
      } catch (XPathParserException ex) {
    	 actual = ex.code();
      } catch (StaticError ex) {
         actual = ex.code();
      } catch (DynamicError ex) {
         actual = ex.code();
      }

      assertXMLEqual("XPath Result Error " + xqFile + ":", expectedResult, actual);
        

   }

   //Test all standard test cases have at least one output file.
   public void test_Catalog002() throws Exception {
      String inputFile = "/XQTSCatalog.xml";
      String xqFile = "/Queries/XQuery/Catalog/Catalog002.xq";
      String resultFile = "/ExpectedTestResults/Catalog/Catalog002.xml";
      String expectedResult = getExpectedResult(resultFile);
      URL fileURL = bundle.getEntry(inputFile);
      loadDOMDocument(fileURL);
      
      // Get XML Schema Information for the Document
      XSModel schema = getGrammar();

      DynamicContext dc = setupDynamicContext(schema);

      String xpath = "$input-context//*:test-case[@scenario = \"standard\"][fn:count(*:output-file) = 0]/@name/string()";
      String actual = null;
      try {
	   	  XPath path = compileXPath(dc, xpath);
	
	      Evaluator eval = new DefaultEvaluator(dc, domDoc);
	      ResultSequence rs = eval.evaluate(path);
         
          actual = "<standard-no-outputfile>" + buildXMLResultString(rs) + "</standard-no-outputfile>";
	
      } catch (XPathParserException ex) {
    	 actual = ex.code();
      } catch (StaticError ex) {
         actual = ex.code();
      } catch (DynamicError ex) {
         actual = ex.code();
      }

      assertXMLEqual("XPath Result Error " + xqFile + ":", expectedResult, actual);
        

   }

   //Test all error test cases have at least one expected error.
   public void test_Catalog003() throws Exception {
      String inputFile = "/XQTSCatalog.xml";
      String xqFile = "/Queries/XQuery/Catalog/Catalog003.xq";
      String resultFile = "/ExpectedTestResults/Catalog/Catalog003.xml";
      String expectedResult = getExpectedResult(resultFile);
      URL fileURL = bundle.getEntry(inputFile);
      loadDOMDocument(fileURL);
      
      // Get XML Schema Information for the Document
      XSModel schema = getGrammar();

      DynamicContext dc = setupDynamicContext(schema);

      String xpath = 
    	  "$input-context//*:test-case[@scenario = \"runtime-error\" or @scenario = \"parse-error\"][fn:count(*:expected-error) = 0 and fn:count(*:output-file[@compare = \"Inspect\"]) = 0]/@name/string()";
      String actual = null;
      try {
	   	  XPath path = compileXPath(dc, xpath);
	
	      Evaluator eval = new DefaultEvaluator(dc, domDoc);
	      ResultSequence rs = eval.evaluate(path);
         
          actual = "<error-no-expected-error>" + buildXMLResultString(rs) + "</error-no-expected-error>";
	
      } catch (XPathParserException ex) {
    	 actual = ex.code();
      } catch (StaticError ex) {
         actual = ex.code();
      } catch (DynamicError ex) {
         actual = ex.code();
      }

      assertXMLEqual("XPath Result Error " + xqFile + ":", expectedResult, actual);
        

   }

}
      