/*******************************************************************************
 * Copyright (c) 2021 Mukul Gandhi, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Mukul Gandhi - initial API and implementation
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.client;

import java.net.URL;
import java.util.ListIterator;

import org.eclipse.wst.xml.xpath2.processor.DefaultEvaluator;
import org.eclipse.wst.xml.xpath2.processor.DynamicContext;
import org.eclipse.wst.xml.xpath2.processor.Evaluator;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;
import org.eclipse.wst.xml.xpath2.processor.ast.XPath;
import org.eclipse.wst.xml.xpath2.processor.internal.Focus;
import org.eclipse.wst.xml.xpath2.processor.internal.types.ElementType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.TextType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSBoolean;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSInteger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * Sample java program, illustrating evaluation of XPath expressions 
 * against XML documents associated with optional XML Schema 
 * documents, using the Eclipse XPath 2.0 processor.
 */
public class XPathClient {
	
	// change value of this variable, for local environment
	private static String DOC_BASE_PATH = "file:///d:/eclipseWorkspaces/eclipse_xpath2_3_2_maintenance/";
	
	private static String XML_FILE_PATH1 = "xpath2client/data/xml/person.xml";
	private static String XSD_FILE_PATH1 = "xpath2client/data/xsd/person.xsd";

	public static void main(String[] args) {
		XPathClient xPathClient = new XPathClient();
		xPathClient.test1();
		xPathClient.test2();
		xPathClient.test3();
		xPathClient.test4();
		xPathClient.test5();
	}
	
	private void test1() {
		try {
		   URL xmlDocUrl = new URL(DOC_BASE_PATH + XML_FILE_PATH1);
		   Document document = XPath2EvaluationUtil.loadDOMDocument(xmlDocUrl);
		   
		   DynamicContext dc = XPath2EvaluationUtil.setupDynamicContext(null, document);
		   // because, no XSD schema is associated with XML instance document,
		   // an explicit cast with 'number' function is needed in an xpath 
		   // expression.
		   String xpath = "count(/persons/person[number(@id) ge 3])";
		   XPath xPath = XPath2EvaluationUtil.compileXPath(dc, xpath);
		   Evaluator eval = new DefaultEvaluator(dc, document);
		   ResultSequence rs = eval.evaluate(xPath);
		   
		   XSInteger xsInteger = (XSInteger)rs.first();
		   System.out.println("Node count : " + xsInteger.getValue());
		}
		catch (Exception ex) {
		   ex.printStackTrace();
		}
	}
	
	private void test2() {
		try {
		   URL xmlDocUrl = new URL(DOC_BASE_PATH + XML_FILE_PATH1);
		   URL xsdDocUrl = new URL(DOC_BASE_PATH + XSD_FILE_PATH1);
		   Document document = XPath2EvaluationUtil.loadDOMDocument(xmlDocUrl, 
				                                                    xsdDocUrl);
		   
		   DynamicContext dc = XPath2EvaluationUtil.setupDynamicContext(null, document);
		   String xpath = "count(/persons/person[@id ge 4])";
		   XPath xPath = XPath2EvaluationUtil.compileXPath(dc, xpath);
		   Evaluator eval = new DefaultEvaluator(dc, document);
		   ResultSequence rs = eval.evaluate(xPath);
		   
		   XSInteger xsInteger = (XSInteger)rs.first();
		   System.out.println("Node count : " + xsInteger.getValue());
		}
		catch (Exception ex) {
		   ex.printStackTrace();
		}
	}
	
	private void test3() {
		try {
		   URL xmlDocUrl = new URL(DOC_BASE_PATH + XML_FILE_PATH1);
		   URL xsdDocUrl = new URL(DOC_BASE_PATH + XSD_FILE_PATH1);
		   Document document = XPath2EvaluationUtil.loadDOMDocument(xmlDocUrl, 
				                                                    xsdDocUrl);
		   
		   DynamicContext dc = XPath2EvaluationUtil.setupDynamicContext(null, document);
		   String xpath = "/persons/person[position() = 3]/@id = 3";
		   XPath xPath = XPath2EvaluationUtil.compileXPath(dc, xpath);
		   Evaluator eval = new DefaultEvaluator(dc, document);
		   ResultSequence rs = eval.evaluate(xPath);
		   
		   XSBoolean xsBoolean = (XSBoolean)rs.first();
		   System.out.println("Result : " + xsBoolean.value());
		}
		catch (Exception ex) {
		   ex.printStackTrace();
		}
	}
	
	private void test4() {
		// demonstrates, evaluating an XPath expression returning
		// a sequence, and printing some of the underlying content of
		// each item of the returned sequence
		try {
		   URL xmlDocUrl = new URL(DOC_BASE_PATH + XML_FILE_PATH1);
		   Document document = XPath2EvaluationUtil.loadDOMDocument(xmlDocUrl);
		   
		   DynamicContext dc = XPath2EvaluationUtil.setupDynamicContext(null, document);
		   String xpath = "/persons/person";
		   XPath xPath = XPath2EvaluationUtil.compileXPath(dc, xpath);
		   Evaluator eval = new DefaultEvaluator(dc, document);
		   ResultSequence rs = eval.evaluate(xPath);
		   ListIterator iter = rs.iterator();
		   StringBuffer sBuffer = new StringBuffer();
		   while (iter.hasNext()) {
			  ElementType item = (ElementType)iter.next();
			  Element elemNode = (Element)item.node_value();
			  Node fNameNode = (elemNode.getElementsByTagName("fName")).item(0);
			  if (iter.hasNext()) {
			     sBuffer.append(fNameNode.getTextContent() + " : ");
			  }
			  else {
				 sBuffer.append(fNameNode.getTextContent()); 
			  }
		   }
		   System.out.println(sBuffer.toString());
		}
		catch (Exception ex) {
		   ex.printStackTrace();
		}
	}
	
	private void test5() {
		// demonstrates, evaluating an XPath expression, with a specific context node
		// somewhere within an XML instance document
		try {
		   URL xmlDocUrl = new URL(DOC_BASE_PATH + XML_FILE_PATH1);
		   Document document = XPath2EvaluationUtil.loadDOMDocument(xmlDocUrl);
		   
		   DynamicContext dc = XPath2EvaluationUtil.setupDynamicContext(null, document);
		   Evaluator eval = new DefaultEvaluator(dc, document);
		   
		   ResultSequence contextNodeResultSet = ResultSequenceFactory.create_new();
		   NodeList nList = document.getElementsByTagName("person");
		   Element contextNode = (Element)nList.item(4);  //   /persons/person[5] is the context node
		   contextNodeResultSet.add(new ElementType(contextNode, dc.node_position(contextNode)));
		   dc.set_focus(new Focus(contextNodeResultSet));
		   
		   String xpath = "fName/text()";
		   XPath xPath = XPath2EvaluationUtil.compileXPath(dc, xpath);
		   ResultSequence rs = eval.evaluate(xPath);
		   System.out.println(((TextType)rs.get(0)).string_value());
		}
		catch (Exception ex) {
		   ex.printStackTrace();
		}
	}

} // XPathClient
