(: Name: op-duration-equal-17 :)
(: Description: Evaluation of duration-equal operator with both operands set to "P36D" and used as argument to xs:boolean (uses ne operator) :)

(: insert-start :)
declare variable $input-context1 external;
(: insert-end :)

xs:boolean(xs:duration("P36D") ne xs:duration("P36D"))