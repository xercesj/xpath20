(: Name: fn-id-dtd-16 :)
(: Description: Evaluation of fn:id for which IDREF list have the same value for all its members but different case. :)

(: insert-start :)
declare variable $input-context1 external;
(: insert-end :)

fn:id("id1 ID1", $input-context1/IDS)