(: Name: preceding-sibling-19 :)
(: Description: Evaluation of the preceding-sibling axis that is part of a boolean expression ("or" and fn:false()). :)

(: insert-start :)
declare variable $input-context1 external;
(: insert-end :)

($input-context1/works[1]/employee[12]/preceding-sibling::employee) or fn:false()