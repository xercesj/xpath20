(: Name: fn-in-scope-prefixes-6 :)
(: Description: Evaluation of fn:in-scope-prefixes function for a computed constructed element node and a default namesapce declaration.:)

declare default element namespace "http://www.example.com";

(: insert-start :)
declare variable $input-context1 external;
(: insert-end :)

let $seq := fn:in-scope-prefixes(element anElement {"Some content"})
 return (count($seq),$seq=("xml",""))