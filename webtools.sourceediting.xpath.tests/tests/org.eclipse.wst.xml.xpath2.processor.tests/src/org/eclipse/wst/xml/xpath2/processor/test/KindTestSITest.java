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

package org.eclipse.wst.xml.xpath2.processor.test;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.xerces.jaxp.validation.XMLSchemaFactory;
import org.apache.xerces.xs.XSModel;
import org.eclipse.wst.xml.xpath2.processor.*;
import org.eclipse.wst.xml.xpath2.processor.ast.XPath;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSBoolean;
import org.xml.sax.SAXException;

public class KindTestSITest extends AbstractPsychoPathTest {

	public void testElementTest1() throws Exception {
		String inputFile = "/TestSources/SpecialTypes.xml";
		String expectedResult = "true";
		URL fileURL = bundle.getEntry(inputFile);
		Schema jaxpSchema = loadSchema();
		loadDOMDocument(fileURL, jaxpSchema);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);
		dc.add_namespace(null, "http://typedecl");
		// addUserDefinedSimpleTypes(schema, dc);

		String xpath = "/root/InterleaveType instance of element()";
		String actual = null;
		try {
			XPath path = compileXPath(dc, xpath);

			Evaluator eval = new DefaultEvaluator(dc, domDoc);
			ResultSequence rs = eval.evaluate(path);

			actual = buildResultString(rs);

		} catch (XPathParserException ex) {
			actual = ex.code();
		} catch (StaticError ex) {
			actual = ex.code();
		} catch (DynamicError ex) {
			actual = ex.code();
		}

		assertEquals("XPath Result Error:", expectedResult, actual);
	}

	public void testElementTest2() throws Exception {
		String inputFile = "/TestSources/SpecialTypes.xml";
		String expectedResult = "true";
		URL fileURL = bundle.getEntry(inputFile);
		Schema jaxpSchema = loadSchema();
		loadDOMDocument(fileURL, jaxpSchema);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);
		dc.add_namespace(null, "http://typedecl");
		// addUserDefinedSimpleTypes(schema, dc);

		String xpath = "/root/InterleaveType instance of element(InterleaveType)";
		String actual = null;
		try {
			XPath path = compileXPath(dc, xpath);

			Evaluator eval = new DefaultEvaluator(dc, domDoc);
			ResultSequence rs = eval.evaluate(path);

			actual = buildResultString(rs);

		} catch (XPathParserException ex) {
			actual = ex.code();
		} catch (StaticError ex) {
			actual = ex.code();
		} catch (DynamicError ex) {
			actual = ex.code();
		}

		assertEquals("XPath Result Error:", expectedResult, actual);
	}

	public void testElementTest3() throws Exception {
		String inputFile = "/TestSources/SpecialTypes.xml";
		String expectedResult = "false";
		URL fileURL = bundle.getEntry(inputFile);
		Schema jaxpSchema = loadSchema();
		loadDOMDocument(fileURL, jaxpSchema);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);
		dc.add_namespace(null, "http://typedecl");
		// addUserDefinedSimpleTypes(schema, dc);

		String xpath = "/root/InterleaveType instance of element(integer)";
		String actual = null;
		try {
			XPath path = compileXPath(dc, xpath);

			Evaluator eval = new DefaultEvaluator(dc, domDoc);
			ResultSequence rs = eval.evaluate(path);

			actual = buildResultString(rs);

		} catch (XPathParserException ex) {
			actual = ex.code();
		} catch (StaticError ex) {
			actual = ex.code();
		} catch (DynamicError ex) {
			actual = ex.code();
		}

		assertEquals("XPath Result Error:", expectedResult, actual);
	}


	public void testXPathInstanceOf4() throws Exception {
		// Bug 298267
		// This test should fail
		String inputFile = "/TestSources/SpecialTypes.xml";
		String expectedResult = "true";
		URL fileURL = bundle.getEntry(inputFile);
		Schema jaxpSchema = loadSchema();
		loadDOMDocument(fileURL, jaxpSchema);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);
		dc.add_namespace(null, "http://typedecl");
		// addUserDefinedSimpleTypes(schema, dc);

		String xpath = "/root/InterleaveType instance of element(InterleaveType, InterleaveType)";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals(expectedResult, actual);

	}

	public void testXPathInstanceOf5() throws Exception {
		// Bug 298267
		// This test should fail
		String inputFile = "/TestSources/SpecialTypes.xml";
		String expectedResult = "false";
		URL fileURL = bundle.getEntry(inputFile);
		Schema jaxpSchema = loadSchema();
		loadDOMDocument(fileURL, jaxpSchema);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);
		dc.add_namespace(null, "http://typedecl");
		// addUserDefinedSimpleTypes(schema, dc);

		String xpath = "/root/InterleaveType instance of element(InterleaveType, InterleaveType2)";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals(expectedResult, actual);

	}
	
	private Schema loadSchema() throws SAXException {
		String schemaFile = "/TestSources/SpecialTypes.xsd";
		SchemaFactory schemaFactory = new XMLSchemaFactory();
		URL schemaURL = bundle.getEntry(schemaFile);
		Schema jaxpschema = schemaFactory.newSchema(schemaURL);
		return jaxpschema;
	}

}
