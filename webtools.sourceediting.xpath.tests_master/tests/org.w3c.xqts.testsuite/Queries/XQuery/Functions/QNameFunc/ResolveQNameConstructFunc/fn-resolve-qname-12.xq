(: Name: fn-resolve-qname-12 :)
(: Description: Evaluation of fn:resolve-qname function for which the given qname has a prefix used together with xs:string and there is a namespace binding with prefix for the element. :)
(: uses fn:namespace-uri-from-qname to get namespace-uri part.  Element is given as direct element:)

(: insert-start :)
declare variable $input-context1 external;
(: insert-end :)

fn:string(fn:namespace-uri-from-QName(fn:resolve-QName(xs:string("p1:anElement"), <anElement xmlns:p1="http://example.com/examples">Some content</anElement>)))