(: Name: preceding-sibling-5 :)
(: Description: Evaluation of the preceding-sibling axis that is part of an "node before" expression (return true). :)

(: insert-start :)
declare variable $input-context1 external;
(: insert-end :)

($input-context1/works[1]/employee[2]/preceding-sibling::employee) << ($input-context1/works[1]/employee[2])