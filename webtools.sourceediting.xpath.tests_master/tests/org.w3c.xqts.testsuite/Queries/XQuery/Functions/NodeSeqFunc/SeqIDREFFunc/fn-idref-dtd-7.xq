(: Name: fn-idref-dtd-7 :)
(: Description: Evaluation of fn:idref with given ID matching multiple elements. :)

(: insert-start :)
declare variable $input-context1 external;
(: insert-end :)

<results>{fn:idref("id4", $input-context1/IDS)}</results>