(: Name: followingsibling-10 :)
(: Description: Evaluation of the following-sibling axis that is part of an "node after" expression with different operands (returns false). :)

(: insert-start :)
declare variable $input-context1 external;
(: insert-end :)

($input-context1/works[1]/employee[12]) >> ($input-context1/works[1]/employee[12]/following-sibling::employee)