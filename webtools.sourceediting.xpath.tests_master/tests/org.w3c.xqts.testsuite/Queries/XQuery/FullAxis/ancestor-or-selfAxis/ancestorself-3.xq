(: Name: ancestorself-3 :)
(: Description: Evaluation of the ancestor-or-self axis that is part of an "is" expression (return true). :)

(: insert-start :)
declare variable $input-context1 external;
(: insert-end :)

($input-context1/works/employee[1]/ancestor-or-self::works) is ($input-context1/works)