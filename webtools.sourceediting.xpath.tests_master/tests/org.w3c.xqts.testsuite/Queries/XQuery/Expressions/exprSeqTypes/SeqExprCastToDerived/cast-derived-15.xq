(: name : cast-derived-15 :)
(: description : Casting from double to a decimal.:)

(: insert-start :)
declare variable $input-context1 external;
(: insert-end :)

let $value := xs:double(10E2)
return $value cast as xs:decimal