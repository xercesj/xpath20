(: Name: preceding-4 :)
(: Description: Evaluation of the preceding axis that is part of an "is" expression (return false). :)

(: insert-start :)
declare variable $input-context1 external;
(: insert-end :)

($input-context1/works[1]/employee[2]/preceding::employee) is ($input-context1/works[1]/employee[2])