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
      
      
public class followingSiblingAxisTest extends AbstractPsychoPathTest {

   //Evaluates unabbreviated syntax. Evaluate "following-sibling::employee[fn:position() = 1]". Selects the next employee sibling of the context node.
   public void test_unabbreviatedSyntax_24() throws Exception {
      String inputFile = "/TestSources/works-mod.xml";
      String xqFile = "/Queries/XQuery/FullAxis/following-siblingAxis/unabbreviatedSyntax-24.xq";
      String resultFile = "/ExpectedTestResults/FullAxis/following-siblingAxis/unabbreviatedSyntax-24.txt";
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
         
          actual = buildXMLResultString(rs);
	
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
      