(: Name: fn-id-dtd-11 :)
(: Description: Evaluation of fn:id with IDREF set to empty string. :)
(: Uses fn:count to avoid empty file. :)

(: insert-start :)
declare variable $input-context1 external;
(: insert-end :)

fn:count(fn:id("", $input-context1/IDS))