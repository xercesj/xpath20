# XPath 2.0
This github repository, contains implementation and test case sources, for an <a href="https://www.w3.org/TR/xpath20">XPath 2.0</a> processor used by 
<a href="https://xerces.apache.org/xerces2-j">Apache Xerces's XML Schema 1.1 processor</a>. This XPath 2.0 processor, can be used as a general purpose 
XPath 2.0 processor as well.

The contents of this repository, are derived from following <a href="https://www.eclipse.org/webtools">Eclipse WTP</a> git repositories (where, the original development 
of this XPath 2.0 processor used to take place):<br/>
https://git.eclipse.org/c/sourceediting/webtools.sourceediting.xpath.git (branch 'R3_2_maintenance'. supports JDK 1.4+),
https://git.eclipse.org/c/sourceediting/webtools.sourceediting.xpath.tests.git (the java code is derived from branch 'R3_2_xpath2_14unittests', supporting JDK 1.5+.
whereas, the input and expected output files for the test cases are derived from 'master' branch).

This XPath 2.0 processor, was originally designed and developed by Andrea Bittau, which he donated to <a href="https://www.eclipse.org">Eclipse Foundation</a>.
The XPath 2.0 processor, was later somewhat improved at Eclipse Foundation, to bring its compliance as with the W3C XPath 2.0 testsuite.
