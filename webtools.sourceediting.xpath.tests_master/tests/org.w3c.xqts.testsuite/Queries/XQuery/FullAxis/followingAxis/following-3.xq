(: Name: following-3 :)
(: Description: Evaluation of the following axis that is part of an "is" expression (return true). :)

(: insert-start :)
declare variable $input-context1 external;
(: insert-end :)

($input-context1/works/employee[12]/following::employee) is ($input-context1/works/employee[13])