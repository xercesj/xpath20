(: Name: followingsibling-4 :)
(: Description: Evaluation of the following-sibling axis that is part of an "is" expression (return false). :)

(: insert-start :)
declare variable $input-context1 external;
(: insert-end :)

($input-context1/works[1]/employee[12]/following-sibling::employee) is ($input-context1/works[1]/employee[12])