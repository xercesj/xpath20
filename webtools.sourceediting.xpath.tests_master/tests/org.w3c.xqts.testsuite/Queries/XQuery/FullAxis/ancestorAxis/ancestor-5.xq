(: Name: ancestor-5 :)
(: Description: Evaluation of the ancestor axis that is part of an "node before" expression (return true). :)

(: insert-start :)
declare variable $input-context1 external;
(: insert-end :)

($input-context1/works/employee[1]/ancestor::works) << ($input-context1/works/employee[1])