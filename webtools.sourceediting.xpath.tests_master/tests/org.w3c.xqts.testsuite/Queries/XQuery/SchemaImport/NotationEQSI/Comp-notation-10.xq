(: Name: Comp-notation-10 :)
(: Description: Evaluation of Notation Comparison operator (ne) and used expression as argument to fn:not function, returns false. :)

(: insert-start :)
import schema namespace myns="http://www.example.com/notation";
declare variable $input-context external;
(: insert-end :)

fn:not($input-context//*:NOTATION1 ne $input-context//*:NOTATION2)
