(: Name: extvardeclwithtype-16 :)
(: Description: Evaluates an external variable that evaluates a boolean expression:)
(: Both queries perform the operation. :)

declare variable $x as xs:boolean external;

$x and fn:false()