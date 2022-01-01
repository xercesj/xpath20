/*******************************************************************************
 * Copyright (c) 2009-2010 Standards for Technology in Automotive Retail and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     David Carver (STAR) - initial API and implementation
 *     Mukul Gandhi - bug 273719 - improvements to fn:string-length function
 *     Mukul Gandhi - bug 273795 - improvements to fn:substring function
 *     Mukul Gandhi - bug 274471 - improvements to fn:string function
 *     Mukul Gandhi - bug 274725 - improvements to fn:base-uri function
 *     Mukul Gandhi - bug 274731 - improvements to fn:document-uri function
 *     Mukul Gandhi - bug 274784 - improvements to xs:boolean data type
 *     Mukul Gandhi - bug 274805 - improvements to xs:integer data type
 *     Mukul Gandhi - bug 274952 - implements xs:long data type
 *     Mukul Gandhi - bug 277599 - implements xs:nonPositiveInteger data type
 *     Mukul Gandhi - bug 277602 - implements xs:negativeInteger data type
 *     Mukul Gandhi - bug 277599 - implements xs:nonPositiveInteger data type
 *     Mukul Gandhi - bug 277608   implements xs:short data type
 *                    bug 277609   implements xs:nonNegativeInteger data type
 *                    bug 277629   implements xs:unsignedLong data type
 *                    bug 277632   implements xs:positiveInteger data type
 *                    bug 277639   implements xs:byte data type
 *                    bug 277642   implements xs:unsignedInt data type
 *                    bug 277645   implements xs:unsignedShort data type
 *                    bug 277650   implements xs:unsignedByte data type
 *                    bug 279373   improvements to multiply operation on xs:yearMonthDuration
 *                                 data type.
 *                    bug 279376   improvements to xs:yearMonthDuration division operation
 *                    bug 281046   implementation of xs:base64Binary data type                                
 *  Jesper S Moller - bug 286061   correct handling of quoted string 
 *  Jesper S Moller - bug 280555 - Add pluggable collation support
 *  Jesper S Moller - bug 297958   Fix fn:nilled for elements
 *  Mukul Gandhi    - bug 298519   improvements to fn:number implementation, catering
 *                                 to node arguments.
 *  Mukul Gandhi    - bug 301539   fix for "context undefined" bug in case of zero
 *                                 arity of fn:name function.
 *  Mukul Gandhi    - bug 309585   implementation of xs:normalizedString data type                             
 * Jesper S Moller  - bug 311480 - fix problem with name matching on keywords 
 * Jesper S Moller  - bug 312191 - instance of test fails with partial matches
 *  Mukul Gandhi    - bug 318313 - improvements to computation of typed values of nodes,
 *                                 when validated by XML Schema primitive types
 *  Mukul Gandhi    - bug 323900 - improving computing the typed value of element &
 *                                 attribute nodes, where the schema type of nodes
 *                                 are simple, with varieties 'list' and 'union'.
 *  Mukul Gandhi    - bug 325262 - providing ability to store an XPath2 sequence into
 *                                 an user-defined variable.
 *  Mukul Gandhi    - bug 334478   implementation of xs:token data type
 *  Mukul Gandhi    - bug 334842 - improving support for the data types Name, NCName, ENTITY, 
 *                                 ID, IDREF and NMTOKEN.
 *  Mukul Gandhi    - bug 338494 - prohibiting xpath expressions starting with / or // to be parsed.
 *  Mukul Gandhi    - bug 338999 - improving compliance of function 'fn:subsequence'. implementing full arity support.
 *  Mukul Gandhi    - bug 339025 - fixes to fn:distinct-values function. ability to find distinct values on node items.
 *  Mukul Gandhi    - bug 341862 - improvements to computation of typed value of xs:boolean nodes.
 *  Mukul Gandhi    - bug 343224 - allow user defined simpleType definitions to be available in in-scope schema types 
 *  Mukul Gandhi    - bug 353373 - "preceding" & "following" axes behavior is erroneous
 *  Mukul Gandhi	- bug 360306 - improvements to "resolve-QName" function and xs:QName type implementation
 *  Mukul Gandhi	               added a test case for improvements to "eq" function for anyURI                
 *******************************************************************************/
package org.eclipse.wst.xml.xpath2.processor.test;

import java.math.BigInteger;
import java.net.URL;
import java.util.Comparator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xerces.xs.XSModel;
import org.eclipse.core.runtime.Platform;
import org.eclipse.wst.xml.xpath2.processor.CollationProvider;
import org.eclipse.wst.xml.xpath2.processor.DefaultDynamicContext;
import org.eclipse.wst.xml.xpath2.processor.DefaultEvaluator;
import org.eclipse.wst.xml.xpath2.processor.DynamicContext;
import org.eclipse.wst.xml.xpath2.processor.DynamicError;
import org.eclipse.wst.xml.xpath2.processor.Evaluator;
import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.ResultSequenceFactory;
import org.eclipse.wst.xml.xpath2.processor.XPathParserException;
import org.eclipse.wst.xml.xpath2.processor.ast.XPath;
import org.eclipse.wst.xml.xpath2.processor.internal.types.QName;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSBoolean;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSDecimal;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSDouble;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSDuration;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSFloat;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSInteger;
import org.eclipse.wst.xml.xpath2.processor.internal.types.XSString;
import org.osgi.framework.Bundle;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class TestBugs extends AbstractPsychoPathTest {

	private static final String URN_X_ECLIPSE_XPATH20_FUNKY_COLLATOR = "urn:x-eclipse:xpath20:funky-collator";

	private Bundle bundle;

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		bundle = Platform
				.getBundle("org.eclipse.wst.xml.xpath2.processor.tests");

	}

	public void testNamesWhichAreKeywords() throws Exception {
		// Bug 273719
		URL fileURL = bundle.getEntry("/bugTestFiles/bug311480.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

        // String xpath = "($input-context/atomic:root/atomic:integer) union ($input-context/atomic:root/atomic:integer)";
		String xpath = "(/element/eq eq 'eq') or //child::xs:*";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}
	
	public void testStringLengthWithElementArg() throws Exception {
		// Bug 273719
		URL fileURL = bundle.getEntry("/bugTestFiles/bug273719.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "string-length(x) > 2";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testBug273795Arity2() throws Exception {
		// Bug 273795
		URL fileURL = bundle.getEntry("/bugTestFiles/bug273795.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		// test with arity 2
		String xpath = "substring(x, 3) = 'happy'";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testBug273795Arity3() throws Exception {
		// Bug 273795
		URL fileURL = bundle.getEntry("/bugTestFiles/bug273795.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		// test with arity 3
		String xpath = "substring(x, 3, 4) = 'happ'";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testStringFunctionBug274471() throws Exception {
		// Bug 274471
		URL fileURL = bundle.getEntry("/bugTestFiles/bug274471.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "x/string() = 'unhappy'";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testStringLengthFunctionBug274471() throws Exception {
		// Bug 274471. string-length() with arity 0
		URL fileURL = bundle.getEntry("/bugTestFiles/bug274471.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "x/string-length() = 7";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testNormalizeSpaceFunctionBug274471() throws Exception {
		// Bug 274471. normalize-space() with arity 0
		URL fileURL = bundle.getEntry("/bugTestFiles/bug274471.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "x/normalize-space() = 'unhappy'";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testAnyUriEqualityBug() throws Exception {
		// Bug 274719
		// reusing the XML document from another bug
		URL fileURL = bundle.getEntry("/bugTestFiles/bug274471.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "xs:anyURI('abc') eq xs:anyURI('abc')";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testBaseUriBug() throws Exception {
		// Bug 274725

		// DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// DocumentBuilder docBuilder = dbf.newDocumentBuilder();
		loadDOMDocument(new URL("http://resolved-locally/xml/note.xml"));

		// for testing this bug, we read the XML document from the web.
		// this ensures, that base-uri property of DOM is not null.
		// domDoc = docBuilder.parse("http://resolved-locally/xml/note.xml");

		// we pass XSModel as null for this test case. Otherwise, we would
		// get an exception.
		DynamicContext dc = setupDynamicContext(null);

		String xpath = "base-uri(note) eq xs:anyURI('http://resolved-locally/xml/note.xml')";

		// please note: The below XPath would also work, with base-uri using
		// arity 0.
		// String xpath =
		// "note/base-uri() eq xs:anyURI('http://resolved-locally/xml/note.xml')";

		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testDocumentUriBug() throws Exception {
		// Bug 274731
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbf.newDocumentBuilder();
		
		InputSource inputSource = getTestSource("http://resolved-locally/xml/note.xml");
		domDoc = docBuilder.parse(inputSource);

		DynamicContext dc = setupDynamicContext(null);

		String xpath = "document-uri(/) eq xs:anyURI('http://resolved-locally/xml/note.xml')";

		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = "false";

		if (result != null) {
			actual = result.string_value();
		}

		assertEquals("true", actual);
	}

	public void testBooleanTypeBug() throws Exception {
		// Bug 274784
		// reusing the XML document from another bug
		URL fileURL = bundle.getEntry("/bugTestFiles/bug273719.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "xs:boolean('1') eq xs:boolean('true')";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testDateConstructorBug() throws Exception {
		// Bug 274792
		URL fileURL = bundle.getEntry("/bugTestFiles/bug274792.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "xs:date(x) eq xs:date('2009-01-01')";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testIntegerDataTypeBug() throws Exception {
		// Bug 274805
		URL fileURL = bundle.getEntry("/bugTestFiles/bug274805.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "xs:integer(x) gt 100";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testLongDataType() throws Exception {
		// Bug 274952
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		// long min value is -9223372036854775808
		// and max value can be 9223372036854775807
		String xpath = "xs:long('9223372036854775807') gt 0";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testIntDataType() throws Exception {
		// Bug 275105
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		// int min value is -2147483648
		// and max value can be 2147483647
		String xpath = "xs:int('2147483647') gt 0";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testSchemaAwarenessForAttributes() throws Exception {
		// Bug 276134
		URL fileURL = bundle.getEntry("/bugTestFiles/bug276134.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/bug276134.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "person/@dob eq xs:date('2006-12-10')";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);

	}

	public void testSchemaAwarenessForElements() throws Exception {
		// Bug 276134
		URL fileURL = bundle.getEntry("/bugTestFiles/bug276134_2.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/bug276134_2.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "person/dob eq xs:date('2006-12-10')";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testNilled() throws Exception {
		// This is a terrible shortcoming in the test suite, I'd say
		URL fileURL = bundle.getEntry("/bugTestFiles/bugNilled.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/bugNilled.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		assertTrue(evaluateBoolean(dc, "empty( nilled( / ) )"));
		assertTrue(evaluateBoolean(dc, "empty( nilled( /root/@attr1 ) )"));
		assertTrue(evaluateBoolean(dc, "empty( nilled( /root/element1/text() ) )"));

		assertFalse(evaluateBoolean(dc, "nilled(/root/element1)"));
		assertTrue(evaluateBoolean(dc, "nilled(/root/element2)"));
		assertFalse(evaluateBoolean(dc, "nilled(/root/element3)"));
		assertFalse(evaluateBoolean(dc, "nilled(/root/element4)"));
	}

	// I can't stand to see so much duplicated code!!!
	private boolean evaluateBoolean(DynamicContext dc, String xpath) throws Exception {
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		return result.value();
	}

	public void testXSNonPositiveInteger() throws Exception {
		// Bug 277599
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");

		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		// min value of xs:nonPositiveInteger is -INF
		// max value is 0
		String xpath = "xs:nonPositiveInteger('0') eq 0";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testXSNegativeInteger() throws Exception {
		// Bug 277602
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");

		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		// min value of xs:negativeInteger is -INF
		// max value is -1
		String xpath = "xs:negativeInteger('-1') eq -1";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testXSShort() throws Exception {
		// Bug 277608
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");

		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		// min value of xs:short is -32768
		// max value of xs:short is 32767
		String xpath = "xs:short('-32768') eq -32768";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testXSNonNegativeInteger() throws Exception {
		// Bug 277609
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");

		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		// min value of xs:nonNegativeInteger is 0
		// max value of xs:nonNegativeInteger is INF
		String xpath = "xs:nonNegativeInteger('0') eq 0";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testXSUnsignedLong() throws Exception {
		// Bug 277629
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");

		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		// min value of xs:unsignedLong is 0
		// max value of xs:unsignedLong is 18446744073709551615
		String xpath = "xs:unsignedLong('0') eq 0";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testXSPositiveInteger() throws Exception {
		// Bug 277632
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");

		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		// min value of xs:positiveInteger is 1
		// max value of xs:positiveInteger is INF
		String xpath = "xs:positiveInteger('1') eq 1";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testXSByte() throws Exception {
		// Bug 277639
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");

		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		// min value of xs:byte is -128
		// max value of xs:byte is 127
		String xpath = "xs:byte('-128') eq -128";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testXSUnsignedInt() throws Exception {
		// Bug 277642
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");

		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		// min value of xs:unsignedInt is 0
		// max value of xs:unsignedInt is 4294967295
		String xpath = "xs:unsignedInt('4294967295') eq xs:unsignedInt('4294967295')";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testXSUnsignedShort() throws Exception {
		// Bug 277645
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		// min value of xs:unsignedShort is 0
		// max value of xs:unsignedShort is 65535
		String xpath = "xs:unsignedShort('65535') eq 65535";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testXSYearMonthDurationMultiply() throws Exception {
		// Bug 279373
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "xs:yearMonthDuration('P2Y11M') * 2.3";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSDuration result = (XSDuration) rs.first();

		String actual = result.string_value();

		assertEquals("P6Y9M", actual);
	}

	public void testXSYearMonthDurationDivide1() throws Exception {
		// Bug 279376
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "xs:yearMonthDuration('P2Y11M') div 1.5";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSDuration result = (XSDuration) rs.first();

		String actual = result.string_value();

		assertEquals("P1Y11M", actual);
	}

	public void testXSYearMonthDurationDivide2() throws Exception {
		// Bug 279376
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "xs:yearMonthDuration('P3Y4M') div xs:yearMonthDuration('-P1Y4M')";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSDecimal result = (XSDecimal) rs.first();

		String actual = result.string_value();

		assertEquals("-2.5", actual);
	}

	public void testXSDayTimeDurationMultiply() throws Exception {
		// Bug 279377
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "xs:dayTimeDuration('PT2H10M') * 2.1";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSDuration result = (XSDuration) rs.first();

		String actual = result.string_value();

		assertEquals("PT4H33M", actual);
	}

	public void testXSDayTimeDurationDivide() throws Exception {
		// Bug 279377
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "xs:dayTimeDuration('P1DT2H30M10.5S') div 1.5";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSDuration result = (XSDuration) rs.first();

		String actual = result.string_value();

		assertEquals("PT17H40M7S", actual);
	}

	public void testNegativeZeroDouble() throws Exception {
		// Bug 279406
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "-(xs:double('0'))";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSDouble result = (XSDouble) rs.first();

		String actual = result.string_value();

		assertEquals("-0", actual);
	}

	public void testNegativeZeroFloat() throws Exception {
		// Bug 279406
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "-(xs:float('0'))";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSFloat result = (XSFloat) rs.first();

		String actual = result.string_value();

		assertEquals("-0", actual);
	}

	public void testXSUnsignedByte() throws Exception {
		// Bug 277650
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		// min value of xs:unsignedByte is 0
		// max value of xs:unsignedByte is 255
		String xpath = "xs:unsignedByte('255') eq 255";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testXSBase64Binary() throws Exception {
		// Bug 281046
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "xs:base64Binary('cmxjZ3R4c3JidnllcmVuZG91aWpsbXV5Z2NhamxpcmJkaWFhbmFob2VsYXVwZmJ1Z2dmanl2eHlzYmhheXFtZXR0anV2dG1q') eq xs:base64Binary('cmxjZ3R4c3JidnllcmVuZG91aWpsbXV5Z2NhamxpcmJkaWFhbmFob2VsYXVwZmJ1Z2dmanl2eHlzYmhheXFtZXR0anV2dG1q')";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testXSHexBinary() throws Exception {
		// Bug 281054
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "xs:hexBinary('767479716c6a647663') eq xs:hexBinary('767479716c6a647663')";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testElementTypedValue() throws Exception {
		// test for fix in ElementType.java, involving incorrectly computing
		// typed value of element node, in case of validating element node,
		// with a user defined simple XSD type.
		URL fileURL = bundle.getEntry("/bugTestFiles/elementTypedValueBug.xml");
		URL schemaURL = bundle
				.getEntry("/bugTestFiles/elementTypedValueBug.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "Example/Transportation/mode eq 'air'";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);

	}

	public void testBug286061_quoted_string_literals_no_normalize()
			throws Exception {

		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "'\"\"'"; // the expression '""' contains no escapes

		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		String resultValue = rs.first().string_value();

		assertEquals("\"\"", resultValue);
	}

	public void testBug286061_quoted_string_literals() throws Exception {

		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "concat(  'Don''t try this' ,  \" at \"\"home\"\",\"  ,  ' she said'  )";

		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		String resultValue = rs.first().string_value();

		assertEquals("Don't try this at \"home\", she said", resultValue);
	}

	public void testBug280555_collations() throws Exception {
		// Setup context
		DefaultDynamicContext dc = setupDynamicContext(null);
		dc.set_collation_provider(createLengthCollatorProvider());
		Evaluator eval = new DefaultEvaluator(dc, domDoc);

		// Parse expression
		XPath path = compileXPath(dc, " 'abc' < 'de' ");

		// Evaluate once
		XSBoolean bval = (XSBoolean) eval.evaluate(path).first();
		assertTrue("'abc' < 'def' for normal collations", bval.value());

		// Evaluate again with the funny collator
		dc.set_default_collation(URN_X_ECLIPSE_XPATH20_FUNKY_COLLATOR);
		XSBoolean bval2 = (XSBoolean) eval.evaluate(path).first();
		assertFalse("'abc' < 'def' for normal collations", bval2.value());
	}

	public void testXPathDefaultNamespace() throws Exception {
		// Test for the fix, for xpathDefaultNamespace
		URL fileURL = bundle
				.getEntry("/bugTestFiles/xpathDefNamespaceTest.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		// set up XPath default namespace in Dynamic Context
		DynamicContext dc = setupDynamicContext(schema);
		addXPathDefaultNamespace("http://xyz");

		String xpath = "X/message = 'hello'";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}
	
	public void testXPathInstanceOf1() throws Exception {
		// Bug 298267
		URL fileURL = bundle.getEntry("/bugTestFiles/elementTypedValueBug.xml");
		URL schemaURL = bundle
				.getEntry("/bugTestFiles/elementTypedValueBug.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "Example/Transportation/mode instance of element()";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}
	
	public void testXPathInstanceOf2() throws Exception {
		// Bug 298267
		URL fileURL = bundle.getEntry("/bugTestFiles/elementTypedValueBug.xml");
		URL schemaURL = bundle
				.getEntry("/bugTestFiles/elementTypedValueBug.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "Example/Transportation/mode instance of element(mode)";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}
	
	public void testXPathInstanceOf3() throws Exception {
		// Bug 298267
		URL fileURL = bundle.getEntry("/bugTestFiles/elementTypedValueBug.xml");
		URL schemaURL = bundle
				.getEntry("/bugTestFiles/elementTypedValueBug.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "Example/Transportation/mode instance of element(mode, modeType)";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}
	
	public void testXPathInstanceOf4() throws Exception {
		// Bug 298267
		URL fileURL = bundle.getEntry("/bugTestFiles/elementTypedValueBug.xml");
		URL schemaURL = bundle
				.getEntry("/bugTestFiles/elementTypedValueBug.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "Example/Transportation/mode instance of element(mode, abc)";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("false", actual);
	}
	
	public void testXPathInstanceOf5() throws Exception {
		// Bug 298267
		URL fileURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "Example/x instance of element(x, x_Type)*";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}

	public void testXPathInstanceOf5_2() throws Exception {
		// Bug 298267
		URL fileURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "(/Example/x, /Example) instance of element(x, x_Type)+";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		assertEquals(false, result.value());
	}

	public void testXPathInstanceOf5_3() throws Exception {
		// Bug 298267
		URL fileURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "(/Example/x, /Example/x) instance of element(x, x_Type)";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		assertFalse(result.value());
	}

	public void testXPathInstanceOf5_4() throws Exception {
		// Bug 298267
		URL fileURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "(/Example/x, /Example/x) instance of element(x, x_Type)+";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		assertTrue(result.value());
	}

	public void testXPathInstanceOf5_5() throws Exception {
		// Bug 298267
		URL fileURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		assertXPathFalse("/Example/x instance of x_Type+", dc, domDoc);
		assertXPathTrue("/Example/x instance of element(*, x_Type)+", dc, domDoc);
		assertXPathTrue("/Example/x instance of element(x, x_Type)+", dc, domDoc);
		assertXPathTrue("not (/Example/x instance of element(z, x_Type)+)", dc, domDoc);
		assertXPathFalse("/Example/x[2]/@mesg instance of mesg_Type", dc, domDoc);
		assertXPathTrue("/Example/x[2]/@mesg instance of attribute(mesg, mesg_Type)", dc, domDoc);
		assertXPathTrue("not (/Example/x[2]/@mesg instance of attribute(cesc, mesg_Type))", dc, domDoc);
	}

	public void testXPathInstanceOf6() throws Exception {
		// Bug 298267
		URL fileURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "Example/x instance of element(*, x_Type)*";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}
	
	public void testXPathInstanceOf7() throws Exception {
		// Bug 298267
		URL fileURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "Example/x instance of element(x, x_Type)+";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}
	
	public void testXPathNotInstanceOf() throws Exception {
		// Bug 298267
		URL fileURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xsd");
	
		loadDOMDocument(fileURL, schemaURL);
	
		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);
	
		DynamicContext dc = setupDynamicContext(schema);
	
		String xpath = "/Example/x[1] instance of element(*, x_Type) and not (/Example/x[1] instance of element(*, y_Type))";
		XPath path = compileXPath(dc, xpath);
	
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);
	
		XSBoolean result = (XSBoolean) rs.first();
	
		String actual = result.string_value();
	
		assertEquals("true", actual);
	}

	public void testXPathInstanceNonExistantElement() throws Exception {
		// Bug 298267
		URL fileURL = bundle.getEntry("/bugTestFiles/elementTypedValueBug.xml");
		URL schemaURL = bundle
				.getEntry("/bugTestFiles/elementTypedValueBug.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "Example/Transportation/mode instance of element(x)";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("false", actual);
	}
	
	public void testFnNumber_Evaluation1() throws Exception {
		// Bug 298519
		URL fileURL = bundle.getEntry("/bugTestFiles/fnNumberBug.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/fnNumberBug.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "number(Example/x) ge 18";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}
	
	public void testFnNumber_Evaluation2() throws Exception {
		// Bug 298519
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "number(xs:unsignedByte('20')) ge 18";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}
	
	public void testAttrNode_Test1() throws Exception {
		// Bug 298535
		URL fileURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "Example/x[1]/@mesg instance of attribute()";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}
	
	public void testAttrNode_Test2() throws Exception {
		// Bug 298535
		URL fileURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "Example/x[1]/@mesg instance of attribute(xx)";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("false", actual);
	}
	
	public void testAttrNode_Test3() throws Exception {
		// Bug 298535
		URL fileURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "Example/x[1]/@mesg instance of attribute(*, mesg_Type)";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}
	
	public void testAttrNode_Test4() throws Exception {
		// Bug 298535
		URL fileURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "Example/x[1]/@mesg instance of attribute(*, abc)";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("false", actual);
	}
	
	public void testAttrNode_Test5() throws Exception {
		// Bug 298535
		URL fileURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "Example/x[1]/@mesg instance of attribute(mesg, mesg_Type)";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}
	
	public void testAttrNode_Test6() throws Exception {
		// Bug 298535
		URL fileURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "Example/x[1]/@mesg instance of attribute(mesg, abc)";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("false", actual);
	}
	
	public void testAttrNode_Test7() throws Exception {
		// Bug 298535
		URL fileURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "Example/x/@mesg instance of attribute(mesg, mesg_Type)*";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}
	
	public void testFnNameContextUndefined() throws Exception {
		// Bug 301539
		URL fileURL = bundle.getEntry("/bugTestFiles/attrNodeTest.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "Example/*[1]/name() eq 'x'";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}
	
	public void testXSNormalizedString() throws Exception {
		// Bug 309585
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "xs:normalizedString('abcs\t') eq xs:normalizedString('abcs')";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		
		boolean testSuccess = false;
		try {
		  ResultSequence rs = eval.evaluate(path);
		}
		catch(DynamicError ex) {
		  // a 'DynamicError' exception indicates, that this test is a success 
		  testSuccess = true;
		}
		
		assertTrue(testSuccess);
	}
	
	public void testParseElementKeywordsAsNodes() throws Exception {
		// Bug 311480
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "/element/attribute";
		XPath path = compileXPath(dc, xpath);
	}
	
	public void testTypedValueEnhancement_primitiveTypes() throws Exception {
		// Bug 318313
		URL fileURL = bundle.getEntry("/bugTestFiles/bug318313.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/bug318313.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "X gt 99";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}
	
	public void testTypedValueEnhancement_Bug323900_1() throws Exception {
		// Bug 323900
		URL fileURL = bundle.getEntry("/bugTestFiles/bug323900_1.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/bug323900_1.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "data(X) instance of xs:integer+";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}
	
	public void testTypedValueEnhancement_Bug323900_2() throws Exception {
		// Bug 323900
		URL fileURL = bundle.getEntry("/bugTestFiles/bug323900_2.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/bug323900_2.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		// 1st test
		String xpath = "data(X) instance of xs:integer+";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("false", actual);
	}
	
	public void testTypedValueEnhancement_Bug323900_3() throws Exception {
		// Bug 323900
		URL fileURL = bundle.getEntry("/bugTestFiles/bug323900_2.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/bug323900_2.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);
		String xpath = "data(X)";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);
		
		boolean result1 = ((XSInteger) rs.get(0)).eq(new XSInteger(BigInteger.
				                                     valueOf(1)), dc);
		boolean result2 = ((XSInteger) rs.get(1)).eq(new XSInteger(BigInteger.
				                                     valueOf(2)), dc);
		boolean result3 = ((XSString) rs.get(2)).eq(new XSString("3.3"), dc);
		
		assertEquals(true, result1 && result2 && result3);
	}
	
	public void testTypedValueEnhancement_Bug323900_4() throws Exception {
		// Bug 323900
		URL fileURL = bundle.getEntry("/bugTestFiles/bug323900_3.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/bug323900_3.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);
		String xpath = "data(X)";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);
        
		XSString result = (XSString) rs.get(0);
		assertEquals("3.3", result.string_value());
	}
	
	public void testTypedValueEnhancement_Bug323900_5() throws Exception {
		// Bug 323900
		URL fileURL = bundle.getEntry("/bugTestFiles/bug323900_4.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/bug323900_3.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);
		String xpath = "data(X)";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);
        
		XSInteger result = (XSInteger) rs.get(0);
		assertEquals("10", result.string_value());
	}
	
	public void testTypedValueEnhancement_BugUsingSeqIntoVariable_1() 
	                                                       throws Exception {
		// Bug 325262
		DynamicContext dc = setupDynamicContext(null);
		
        ResultSequence rs = ResultSequenceFactory.create_new();
        dc.set_variable(new QName("value"), rs);
        
		String xpath = "deep-equal($value,())";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rsRes = eval.evaluate(path);
        
		XSBoolean result = (XSBoolean) rsRes.get(0);
		assertEquals("true", result.string_value());
	}
	
	public void testTypedValueEnhancement_BugUsingSeqIntoVariable_2() 
	                                                       throws Exception {
		// Bug 325262
		DynamicContext dc = setupDynamicContext(null);
		
        ResultSequence rs = ResultSequenceFactory.create_new();
        rs.add(new XSInteger(BigInteger.valueOf(2)));
        rs.add(new XSInteger(BigInteger.valueOf(4)));
        rs.add(new XSInteger(BigInteger.valueOf(6)));
        dc.set_variable(new QName("value"), rs);
        
        // test a
		String xpath = "$value instance of xs:integer+";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rsRes = eval.evaluate(path);        
		XSBoolean result = (XSBoolean) rsRes.get(0);
		assertEquals("true", result.string_value());
		
		// test b
		xpath = "deep-equal($value, (2, 4, 6))";
		path = compileXPath(dc, xpath);
		eval = new DefaultEvaluator(dc, domDoc);
		rsRes = eval.evaluate(path);        
		result = (XSBoolean) rsRes.get(0);
		assertEquals("true", result.string_value());
	}
	
	public void testTypedValueEnhancement_BugUsingSeqIntoVariable_3() 
	                                                      throws Exception {
		// Bug 325262
		DynamicContext dc = setupDynamicContext(null);
		
		ResultSequence rs = ResultSequenceFactory.create_new();
        rs.add(new XSInteger(BigInteger.valueOf(2)));
        rs.add(new XSInteger(BigInteger.valueOf(4)));
        rs.add(new XSInteger(BigInteger.valueOf(6)));
        dc.set_variable(new QName("value"), rs);
        
		String xpath = "count(data($value)) = 3";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rsRes = eval.evaluate(path);        
		XSBoolean result = (XSBoolean) rsRes.get(0);
		assertEquals("true", result.string_value());
	}
	
	public void testXSToken() throws Exception {
		// Bug 334478
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		// the strings in below are not valid tokens (they contain 2 consecutive spaces)
		String xpath = "xs:token('abcs  abcde') eq xs:token('abcs  abcde')";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		
		boolean testSuccess = false;
		try {
		   ResultSequence rs = eval.evaluate(path);
		}
		catch(DynamicError ex) {
		   // a 'DynamicError' exception indicates, that this test is a success 
		   testSuccess = true;
		}
		
		assertTrue(testSuccess);
	}
	
	public void testBug334842() throws Exception {
		// Bug 334842
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		// test a)
		String xpath = "xs:Name('x:abc') eq xs:Name('x:abc')"; 
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rsRes = eval.evaluate(path);
		XSBoolean result = (XSBoolean) rsRes.get(0);
		assertEquals("true", result.string_value());
		
		// test b)
		xpath = "xs:NCName('x:abc') eq xs:NCName('x:abc')"; 
		path = compileXPath(dc, xpath);
		try {
		   rsRes = eval.evaluate(path);
		   assertTrue(false);
		}
		catch(DynamicError ex) {
		   // a 'DynamicError' exception indicates, that this test is a success 
		   assertTrue(true);
		}
		
		// test c)
		xpath = "xs:NCName('abc') eq xs:NCName('abc')"; 
		path = compileXPath(dc, xpath);
		rsRes = eval.evaluate(path);
		result = (XSBoolean) rsRes.get(0);
		assertEquals("true", result.string_value());
		
		// test d)
		xpath = "xs:ID('x:abc') eq xs:ID('x:abc')"; 
		path = compileXPath(dc, xpath);
		try {
		   rsRes = eval.evaluate(path);
		   assertTrue(false);
		}
		catch(DynamicError ex) {
		   // a 'DynamicError' exception indicates, that this test is a success 
		   assertTrue(true);
		}
		
		// test e)
		xpath = "xs:ID('abc') eq xs:ID('abc')"; 
		path = compileXPath(dc, xpath);
		rsRes = eval.evaluate(path);
		result = (XSBoolean) rsRes.get(0);
		assertEquals("true", result.string_value());
	}
	
	public void testDefNamespaceOnbuiltInTypes() throws Exception {
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		// set up XPath default namespace in Dynamic Context
		DynamicContext dc = setupDynamicContext(schema);
        dc.set_variable(new QName("value"), new XSString("2.5"));
		addXPathDefaultNamespace("http://www.w3.org/2001/XMLSchema");

		String xpath = "$value castable as double";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, (Document)null);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();

		String actual = result.string_value();

		assertEquals("true", actual);
	}
	
	public void testExprParsingBeginnigWithRootNode_bug338494() throws Exception {
		// Bug 338494
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		// test a)
		String xpath = "/x";
		XPath path = null;
		try {
		    path = compileXPath(dc, xpath, true);
		    // test fails
		    assertTrue(false);
		}
		catch(XPathParserException ex) {
			if ("Expression starts with / or //".equals(ex.getMessage())) {
				// test passes
				assertTrue(true);
			}
		}
		
		// test b)
		xpath = "//x";
		try {
		    path = compileXPath(dc, xpath, true);
		    // test fails
		    assertTrue(false);
		}
		catch(XPathParserException ex) {
			if ("Expression starts with / or //".equals(ex.getMessage())) {
				// test passes
				assertTrue(true);
			}
		}
		
		// test c)
		xpath = "/";
		try {
		    path = compileXPath(dc, xpath, true);
		    // test fails
		    assertTrue(false);
		}
		catch(XPathParserException ex) {
			if ("Expression starts with / or //".equals(ex.getMessage())) {
				// test passes
				assertTrue(true);
			}
		}
		
		// test d)
		xpath = "x/y[/a]";
		try {
		    path = compileXPath(dc, xpath, true);
		    // test fails
		    assertTrue(false);
		}
		catch(XPathParserException ex) {
			if ("Expression starts with / or //".equals(ex.getMessage())) {
				// test passes
				assertTrue(true);
			}
		}
		
		// test e)
		xpath = ".//x";
		try {
		    path = compileXPath(dc, xpath, true);
		    // test passes
		    assertTrue(true);
		}
		catch(XPathParserException ex) {
		   // test fails
		   assertTrue(false);
		}
	}
	
	public void testBug338999_Fnsubsequence() throws Exception {
		// bug 338999
		URL fileURL = bundle.getEntry("/bugTestFiles/bug338999.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/bug338999.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);
		
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		
		// test a)
		String xpath = "count(subsequence(X/*, 2)) eq 2";
		XPath path = compileXPath(dc, xpath);		
		ResultSequence rs = eval.evaluate(path);
		String actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("true", actual);
		
		// test b)
		xpath = "subsequence(X/*, 2) instance of element(*, xs:integer)+";
		path = compileXPath(dc, xpath);		
		rs = eval.evaluate(path);
		actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("true", actual);
		
		// test c)
		xpath = "deep-equal(subsequence((1,2,3,4), 2), (2,3,4))";
		path = compileXPath(dc, xpath);		
		rs = eval.evaluate(path);
		actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("true", actual);
		
		// test d)
		// hetrogeneous sequence as input. arity 3 mode.
		xpath = "deep-equal(subsequence(('a', 1, 1.5), 2, 2), (1, 1.5))";
		path = compileXPath(dc, xpath);		
		rs = eval.evaluate(path);
		actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("true", actual);
		
		// test e)
		// hetrogeneous sequence as input. arity 3 mode (startingLoc is < 0).
		xpath = "deep-equal(subsequence(('a', 1, 1.5, 'b'), -2, 3), ())";
		path = compileXPath(dc, xpath);		
		rs = eval.evaluate(path);
		actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("true", actual);
	}
	
	public void testBug339025_distinctValuesOnNodeSequence() throws Exception {
		// bug 339025
		URL fileURL = bundle.getEntry("/bugTestFiles/bug339025.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/bug339025.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);
		
		DynamicContext dc = setupDynamicContext(schema);;		
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		
		// test a)
		String xpath = "count(//a) = count(distinct-values(//a))";
		XPath path = compileXPath(dc, xpath);		
		ResultSequence rs = eval.evaluate(path);
		String actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("true", actual);
		
		// test b)
		xpath = "count(X/a) = count(distinct-values(X/a))";
		path = compileXPath(dc, xpath);		
		rs = eval.evaluate(path);
		actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("true", actual);
		
		// test c)
		xpath = "count(//b) = count(distinct-values(//b))";
		path = compileXPath(dc, xpath);		
		rs = eval.evaluate(path);
		actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("false", actual);
	}
	
	public void testBug341862_TypedBooleanNode() throws Exception {
		// bug 341862
		URL fileURL = bundle.getEntry("/bugTestFiles/bug341862.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/bug341862.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);
		
		DynamicContext dc = setupDynamicContext(schema);;		
		Evaluator eval = new DefaultEvaluator(dc, domDoc);

		// test a)
		String xpath = "/X/a[1] = true()";
		XPath path = compileXPath(dc, xpath);		
		ResultSequence rs = eval.evaluate(path);
		String actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("true", actual);
		
		// test b)
		xpath = "/X/a[1]/@att = true()";
		path = compileXPath(dc, xpath);		
		rs = eval.evaluate(path);
		actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("true", actual);
		
		// test c)
		xpath = "/X/a[2] = true()";
		path = compileXPath(dc, xpath);		
		rs = eval.evaluate(path);
		actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("true", actual);
		
		// test d)
		xpath = "/X/a[2]/@att = true()";
		path = compileXPath(dc, xpath);		
		rs = eval.evaluate(path);
		actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("true", actual);
		
		// test e)
		xpath = "/X/a[3] = false()";
		path = compileXPath(dc, xpath);		
		rs = eval.evaluate(path);
		actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("true", actual);
		
		// test f)
		xpath = "/X/a[3]/@att = false()";
		path = compileXPath(dc, xpath);		
		rs = eval.evaluate(path);
		actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("true", actual);
		
		// test g)
		xpath = "/X/a[4] = false()";
		path = compileXPath(dc, xpath);		
		rs = eval.evaluate(path);
		actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("true", actual);
		
		// test h)
		xpath = "/X/a[4]/@att = false()";
		path = compileXPath(dc, xpath);		
		rs = eval.evaluate(path);
		actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("true", actual);
	}
	
	public void testBug_343224() throws Exception {
		// bug 343224
		URL fileURL = bundle.getEntry("/bugTestFiles/bug343224.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/bug343224.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);
		
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		
		// test a)
		String xpath = "X/a[1] castable as STATUS";
		XPath path = compileXPath(dc, xpath);		
		ResultSequence rs = eval.evaluate(path);
		String actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("true", actual);
		
		// test b)
		xpath = "X/a[2] castable as STATUS";
		path = compileXPath(dc, xpath);		
		rs = eval.evaluate(path);
		actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("true", actual);
		
		// test c)
		xpath = "X/a[3] castable as STATUS";
		path = compileXPath(dc, xpath);		
		rs = eval.evaluate(path);
		actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("false", actual);
	}
	
	public void testReverse_axes() throws Exception {
		// Bug 353373
		URL fileURL = bundle.getEntry("/bugTestFiles/bug353373_1.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		// test (a)
		String xpath = "count(x/q/preceding-sibling::*) = 2";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);
		XSBoolean result = (XSBoolean) rs.first();
		String actual = result.string_value();
		assertEquals("true", actual);
		
		// test (b)
		xpath = "deep-equal((x/q/preceding-sibling::*[1]/name(),x/q/preceding-sibling::*[2]/name()),('p','uu'))";
		path = compileXPath(dc, xpath);
		eval = new DefaultEvaluator(dc, domDoc);
		rs = eval.evaluate(path);
		result = (XSBoolean) rs.first();
		actual = result.string_value();
		assertEquals("true", actual);
		
		// test (c)
		xpath = "count(//u/preceding::*) = 7";
		path = compileXPath(dc, xpath);
		eval = new DefaultEvaluator(dc, domDoc);
		rs = eval.evaluate(path);
		result = (XSBoolean) rs.first();
		actual = result.string_value();
		assertEquals("true", actual);
		
		// test (d)
		xpath = "deep-equal((//u/preceding::*[1]/name(),//u/preceding::*[2]/name(),//u/preceding::*[3]/name(),//u/preceding::*[4]/name(),//u/preceding::*[5]/name(),//u/preceding::*[6]/name(),//u/preceding::*[7]/name()), " +
				           "('m2','m1','z','c','q','p','uu'))";
		path = compileXPath(dc, xpath);
		eval = new DefaultEvaluator(dc, domDoc);
		rs = eval.evaluate(path);
		result = (XSBoolean) rs.first();
		actual = result.string_value();
		assertEquals("true", actual);
		
		// test (e)
		xpath = "count(//u/ancestor::*) = 2";
		path = compileXPath(dc, xpath);
		eval = new DefaultEvaluator(dc, domDoc);
		rs = eval.evaluate(path);
		result = (XSBoolean) rs.first();
		actual = result.string_value();
		assertEquals("true", actual);
		
		// test (f)
		xpath = "deep-equal((//u/ancestor::*[1]/name(),//u/ancestor::*[2]/name()),('y','x'))";
		path = compileXPath(dc, xpath);
		eval = new DefaultEvaluator(dc, domDoc);
		rs = eval.evaluate(path);
		result = (XSBoolean) rs.first();
		actual = result.string_value();
		assertEquals("true", actual);
		
		// test (g)
		xpath = "count(//u/ancestor-or-self::*) = 3";
		path = compileXPath(dc, xpath);
		eval = new DefaultEvaluator(dc, domDoc);
		rs = eval.evaluate(path);
		result = (XSBoolean) rs.first();
		actual = result.string_value();
		assertEquals("true", actual);
		
		// test (h)
		xpath = "deep-equal((//u/ancestor-or-self::*[1]/name(),//u/ancestor-or-self::*[2]/name(),//u/ancestor-or-self::*[3]/name()),('u','y','x'))";
		path = compileXPath(dc, xpath);
		eval = new DefaultEvaluator(dc, domDoc);
		rs = eval.evaluate(path);
		result = (XSBoolean) rs.first();
		actual = result.string_value();
		assertEquals("true", actual);
	}
	
	public void testForward_axes() throws Exception {
		// Bug 353373
		URL fileURL = bundle.getEntry("/bugTestFiles/bug353373_2.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		// test (a)
		String xpath = "count(x/q/following-sibling::*) = 2";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);
		XSBoolean result = (XSBoolean) rs.first();
		String actual = result.string_value();
		assertEquals("true", actual);
		
		// test (b)
		xpath = "deep-equal((x/q/following-sibling::*[1]/name(),x/q/following-sibling::*[2]/name()),('c','y'))";
		path = compileXPath(dc, xpath);
		eval = new DefaultEvaluator(dc, domDoc);
		rs = eval.evaluate(path);
		result = (XSBoolean) rs.first();
		actual = result.string_value();
		assertEquals("true", actual);
		
		// test (c)
		xpath = "count(//a5/following::*) = 8";
		path = compileXPath(dc, xpath);
		eval = new DefaultEvaluator(dc, domDoc);
		rs = eval.evaluate(path);
		result = (XSBoolean) rs.first();
		actual = result.string_value();
		assertEquals("true", actual);
		
		// test (d)
		xpath = "deep-equal((//n1/following::*[1]/name(),//n1/following::*[2]/name(),//n1/following::*[3]/name(),//n1/following::*[4]/name(),//n1/following::*[5]/name(),//n1/following::*[6]/name(),//n1/following::*[7]/name(),//n1/following::*[8]/name())," +
				           "('p','q','c','y','z','m1','m2','u'))";
		path = compileXPath(dc, xpath);
		eval = new DefaultEvaluator(dc, domDoc);
		rs = eval.evaluate(path);
		result = (XSBoolean) rs.first();
		actual = result.string_value();
		assertEquals("true", actual);
	}
	
	public void testInstanceOf_1() throws Exception {
		// improvements to "instance of" checks
		
		// reusing files from another bug
		URL fileURL = bundle.getEntry("/bugTestFiles/bug276134.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/bug276134.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);
		
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		
		// test a)
		String xpath = "/person/@id instance of attribute(*, xs:integer)";
		XPath path = compileXPath(dc, xpath);		
		ResultSequence rs = eval.evaluate(path);
		String actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("true", actual);
		
		// test b)
		xpath = "/person/@id instance of xs:integer";
		path = compileXPath(dc, xpath);		
		rs = eval.evaluate(path);
		actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("false", actual);
	}
	
	public void testInstanceOf_2() throws Exception {
		// improvements to "instance of" checks
		
		// reusing files from another bug
		URL fileURL = bundle.getEntry("/bugTestFiles/bug276134.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/bug276134.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);
		
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		
		// test a)
		String xpath = "/person/fname instance of element(*, xs:string)";
		XPath path = compileXPath(dc, xpath);		
		ResultSequence rs = eval.evaluate(path);
		String actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("true", actual);
		
		// test b)
		xpath = "/person/fname instance of xs:string";
		path = compileXPath(dc, xpath);		
		rs = eval.evaluate(path);
		actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("false", actual);
	}
	
	public void testResolveQName_1() throws Exception {
		// Bug 360306
		URL fileURL = bundle.getEntry("/bugTestFiles/resQName.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		// test (a)
		String xpath = "resolve-QName(/messages/message[1]/@kind, /messages) = xs:QName('xs:int')";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);
		XSBoolean result = (XSBoolean) rs.first();
		String actual = result.string_value();
		assertEquals("true", actual);
	}
	
	public void testBug_namespaceUriFromQName_1() throws Exception {
		// improving implementation of fn:namespace-uri-from-QName
		
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);
		dc.add_namespace("p", "http://cta023.com/p");

		// test (a)
		String xpath = "namespace-uri-from-QName(xs:QName('p:ppp')) = 'http://cta023.com/p'";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);
		XSBoolean result = (XSBoolean) rs.first();
		String actual = result.string_value();
		assertEquals("true", actual);
	}
	
	public void testBug_namespaceUriFromQName_2() throws Exception {
		// improving implementation of fn:namespace-uri-from-QName
		
		URL fileURL = bundle.getEntry("/bugTestFiles/namespaceUriFromQName_bug.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);
		dc.add_namespace("a", "http://x.y");

		String xpath = "namespace-uri-from-QName(node-name(a:X)) = 'http://x.y'";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);
		XSBoolean result = (XSBoolean) rs.first();
		String actual = result.string_value();
		assertEquals("true", actual);
	}
	
	public void testNonDocumentNodeAsRoot() throws Exception {
		// a non-document node as root node of the XDM tree. introducing a new argument to DefaultEvaluator. 
		
		// reusing files from another bug
		URL fileURL = bundle.getEntry("/bugTestFiles/bug276134.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/bug276134.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);
		
		Evaluator eval = new DefaultEvaluator(dc, domDoc, domDoc.getDocumentElement());
		
		// test a)
		String xpath = ". is root()";
		XPath path = compileXPath(dc, xpath);		
		ResultSequence rs = eval.evaluate(path);
		String actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("true", actual);
		
		// test b)
		xpath = "empty(..)";
		path = compileXPath(dc, xpath);		
		rs = eval.evaluate(path);
		actual = ((XSBoolean) rs.first()).string_value();
		assertEquals("true", actual);
	}
	
	public void testFnIndexOf_onQName1() throws Exception {
		// fn:index-of finding QName values
		
		URL fileURL = bundle.getEntry("/bugTestFiles/bug338999.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/bug338999.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "index-of(for $e in X/* return node-name($e), node-name(X/b))";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);
		assertTrue(rs.size() > 0);
		String actual = ((XSInteger) rs.first()).string_value();
		assertEquals("2", actual);
	}
	
	public void testFnIndexOf_onQName2() throws Exception {
		// fn:index-of finding QName values
		
		URL fileURL = bundle.getEntry("/bugTestFiles/bug338999.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/bug338999.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "index-of(for $e in X/* return node-name($e), xs:QName('b'))";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);
		assertTrue(rs.size() > 0);
		String actual = ((XSInteger) rs.first()).string_value();
		assertEquals("2", actual);
	}
	
	public void testFnIndexOf_onQName3() throws Exception {
		// fn:index-of finding QName values
		
		URL fileURL = bundle.getEntry("/bugTestFiles/bug338999.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/bug338999.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "index-of(for $e in X/* return node-name($e), fn:QName('', 'b'))";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);
		assertTrue(rs.size() > 0);
		String actual = ((XSInteger) rs.first()).string_value();
		assertEquals("2", actual);
	}
	
	public void testXSDateTimeCast() throws Exception {
		// test explicit casts on xs:dateTime node references
		
		URL fileURL = bundle.getEntry("/bugTestFiles/dateTime1.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/dateTime1.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		// test a)
		String xpath = "exists(xs:dateTime(/test/@t1))";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();
		String actual = result.string_value();
		assertEquals("true", actual);
		
		// test b)
		xpath = "/test/@t1 instance of attribute(t1, xs:dateTime)";
		path = compileXPath(dc, xpath);

		eval = new DefaultEvaluator(dc, domDoc);
		rs = eval.evaluate(path);

		result = (XSBoolean) rs.first();
		actual = result.string_value();
		assertEquals("true", actual);
		
		// test c)
		xpath = "(xs:dateTime(/test/@t1) + xs:dayTimeDuration('P1D')) eq xs:dateTime('2010-10-06T10:05:05')";
		path = compileXPath(dc, xpath);

		eval = new DefaultEvaluator(dc, domDoc);
		rs = eval.evaluate(path);

		result = (XSBoolean) rs.first();
		actual = result.string_value();
		assertEquals("true", actual);
	}
	
	public void testBug_362026() throws Exception {
		// "instance of" must not atomize the LHS before the comparison check
		
		URL fileURL = bundle.getEntry("/bugTestFiles/bug362026.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/bug362026.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		// test a)
		String xpath = "/X/Y instance of xs:integer";
		XPath path = compileXPath(dc, xpath);

		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);

		XSBoolean result = (XSBoolean) rs.first();
		String actual = result.string_value();
		assertEquals("false", actual);
		
		// test b)
		xpath = "/X/@att1 instance of xs:string";
		path = compileXPath(dc, xpath);

		eval = new DefaultEvaluator(dc, domDoc);
		rs = eval.evaluate(path);

		result = (XSBoolean) rs.first();
		actual = result.string_value();
		assertEquals("false", actual);
		
		// test c)
		xpath = "/X/Y instance of element(*, xs:integer)";
		path = compileXPath(dc, xpath);

		eval = new DefaultEvaluator(dc, domDoc);
		rs = eval.evaluate(path);

		result = (XSBoolean) rs.first();
		actual = result.string_value();
		assertEquals("true", actual);
		
		// test d)
		xpath = "/X/@att1 instance of attribute(*, xs:string)";
		path = compileXPath(dc, xpath);

		eval = new DefaultEvaluator(dc, domDoc);
		rs = eval.evaluate(path);

		result = (XSBoolean) rs.first();
		actual = result.string_value();
		assertEquals("true", actual);
	}
	
	public void testBugNonAsciiCharsHandling() throws Exception {
		
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);


		String xpath = "count(Einf�hrung) le 1";
		try {
		   XPath path = compileXPath(dc, xpath, true);
		}
		catch(Exception ex) {
		   assertTrue(false);
		}
	}
	
	public void testBugEQ() throws Exception {
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "1 eq '1'";
		XPath path = compileXPath(dc, xpath);
        boolean successStatus = true;
		try {
			Evaluator eval = new DefaultEvaluator(dc, domDoc);
			ResultSequence rs = eval.evaluate(path);
			assertEquals(false, successStatus);
		}
		catch (DynamicError err) {
			if ("XPTY0004".equals(err.code())) {
				successStatus = false;
			}
		}
	}
	
	public void testBugFnIndexOf() throws Exception {
		
		URL fileURL = bundle.getEntry("/bugTestFiles/indexOf1.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/indexOf1.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "deep-equal(index-of(X/a, 2), (1,4))";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);
		assertEquals(true, ((XSBoolean) rs.first()).value());
		
		xpath = "deep-equal(index-of(X/b, 1), (1,3))";
		path = compileXPath(dc, xpath);
		rs = eval.evaluate(path);
		assertEquals(true, ((XSBoolean) rs.first()).value());
		
		xpath = "deep-equal(index-of(X/@attr, 5), (3,4,5))";
		path = compileXPath(dc, xpath);
		rs = eval.evaluate(path);
		assertEquals(true, ((XSBoolean) rs.first()).value());
	}
	
	public void testBugTypedValue() throws Exception {
		
		URL fileURL = bundle.getEntry("/bugTestFiles/bugTypedValue1.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/bugTypedValue1.xsd");

		loadDOMDocument(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammar(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "data(Z/X[1]/@val) instance of xs:date+";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);
		assertEquals(true, ((XSBoolean) rs.first()).value());
		
		xpath = "count(data(Z/X[1]/@val)) eq 3";
		path = compileXPath(dc, xpath);
		rs = eval.evaluate(path);
		assertEquals(true, ((XSBoolean) rs.first()).value());
	}
	
	public void testBugFnSumOnDurationTypes() throws Exception {
		
		// use XSD 1.1 validation, for this test
		
		URL fileURL = bundle.getEntry("/bugTestFiles/FnSum1.xml");
		URL schemaURL = bundle.getEntry("/bugTestFiles/FnSum1.xsd");

		loadDOMDocumentXS11(fileURL, schemaURL);

		// Get XSModel object for the Schema
		XSModel schema = getGrammarXS11(schemaURL);

		DynamicContext dc = setupDynamicContext(schema);
		dc.add_namespace("tst", "http://www.rackspace.com/test/duration");

		String xpath = "xsd:dayTimeDuration('PT24H') = sum(tst:test/tst:d)";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);
		assertEquals(true, ((XSBoolean) rs.first()).value());
		
		xpath = "xsd:dayTimeDuration('PT24H') = sum(for $d in tst:test/tst:d return $d)";
		path = compileXPath(dc, xpath);
		rs = eval.evaluate(path);
		assertEquals(true, ((XSBoolean) rs.first()).value());
	}
	
	
	public void testXSAnyURI_bug() throws Exception {

		URL fileURL = bundle.getEntry("/bugTestFiles/dashboard.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "count(/dashboard/widget/*[namespace-uri() eq 'http://jaspersoft.com/highcharts']) eq 1";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);
		XSBoolean result = (XSBoolean) rs.first();
		String actual = result.string_value();
		assertEquals("true", actual);
	}

	public void testfnMatches_1() throws Exception {
		
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "matches('111-111-1111', '[0-9]{3}-[0-9]{3}-[0-9]{4}')";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);
		assertEquals(true, ((XSBoolean) rs.first()).value());
	}
	
    public void testfnMatches_2() throws Exception {
		
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "matches('123', '[0-9]{3}', 'x')";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
		ResultSequence rs = eval.evaluate(path);
		assertEquals(true, ((XSBoolean) rs.first()).value());
	}
    
    public void testfnMatches_3() throws Exception {
		
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		// test a)
		String xpath = "matches('123', '[0 - 9]{3}', 'x')";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
 	    ResultSequence rs = eval.evaluate(path);
 	    assertEquals(true, ((XSBoolean) rs.first()).value());
 	    
  	    // test b)
 	    xpath = "matches('123', '[0-9]{3} # comment1', 'x')";
		path = compileXPath(dc, xpath);
	    rs = eval.evaluate(path);
	    assertEquals(false, ((XSBoolean) rs.first()).value());
	    
	    // test c)
 	    xpath = "matches('\n', '.')";
		path = compileXPath(dc, xpath);
	    rs = eval.evaluate(path);
	    assertEquals(false, ((XSBoolean) rs.first()).value());
	    
	    // test d)
 	    xpath = "matches('\r', '.')";
		path = compileXPath(dc, xpath);
	    rs = eval.evaluate(path);
	    assertEquals(false, ((XSBoolean) rs.first()).value());
	    
	    // test e)
 	    xpath = "matches('abc', '.{3}')";
		path = compileXPath(dc, xpath);
	    rs = eval.evaluate(path);
	    assertEquals(true, ((XSBoolean) rs.first()).value());
	    
	    // test f)
 	    xpath = "matches('ab\r', '.{3}')";
		path = compileXPath(dc, xpath);
	    rs = eval.evaluate(path);
	    assertEquals(false, ((XSBoolean) rs.first()).value());
	}
    
    public void testfnReplace() throws Exception {
		
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);
        
		// test the fn:replace function, whose regex argument 
		// has character class subtraction.
		String xpath = "replace('xyzpqr','[a-z-[aeiuo]]','MN')";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
 	    ResultSequence rs = eval.evaluate(path);
 	    assertEquals("MNMNMNMNMNMN", ((XSString) rs.first()).value());
	}
    
    public void testfnTokenize_1() throws Exception {
		
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		String xpath = "deep-equal(tokenize('1,15,,24,50,', ','), ('1', '15', '', '24', '50', ''))";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
 	    ResultSequence rs = eval.evaluate(path);
 	    assertEquals(true, ((XSBoolean) rs.first()).value());
	}
    
    public void testfnTokenize_2() throws Exception {
		
		bundle = Platform.getBundle("org.w3c.xqts.testsuite");
		URL fileURL = bundle.getEntry("/TestSources/emptydoc.xml");
		loadDOMDocument(fileURL);

		// Get XML Schema Information for the Document
		XSModel schema = getGrammar();

		DynamicContext dc = setupDynamicContext(schema);

		// test the fn:tokenize function, whose regex argument 
		// has character class subtraction.
		String xpath = "deep-equal(tokenize('abc','[a-z-[aeiuo]]'), ('a','',''))";
		XPath path = compileXPath(dc, xpath);
		Evaluator eval = new DefaultEvaluator(dc, domDoc);
 	    ResultSequence rs = eval.evaluate(path);
 	    assertEquals(true, ((XSBoolean) rs.first()).value());
	}
	
	private CollationProvider createLengthCollatorProvider() {
		return new CollationProvider() {
			@SuppressWarnings("unchecked")
			public Comparator get_collation(String name) {
				if (name.equals(URN_X_ECLIPSE_XPATH20_FUNKY_COLLATOR)) {
					return new Comparator<String>() {
						public int compare(String o1, String o2) {
							return o1.length() - o2.length();
						}
					};
				}
				return null;
			}
		};
	}
	
}
