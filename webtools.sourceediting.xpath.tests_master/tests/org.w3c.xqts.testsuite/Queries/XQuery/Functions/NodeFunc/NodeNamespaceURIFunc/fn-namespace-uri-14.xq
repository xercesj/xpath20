(: Name: fn-namespace-uri-14:)
(: Description: Evaluation of the fn:namespace-uri function argument set to an element node with no namespace queried from a file.:)
(: Use the fn:count function to avoid empty file. :)

(: insert-start :)
declare variable $input-context1 external;
(: insert-end :)

fn:count(fn:namespace-uri($input-context1/works/employee[1]))