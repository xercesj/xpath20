(: Name: ForExprType050 :)
(: Description: FLWOR expressions with type declaration. Non-trivial value type promotion: Union type integer|decimal -> decimal :)

(: insert-start :)
import schema default element namespace "http://typedecl";

declare variable $input-context external;
(: insert-end :)

for $test as xs:decimal in data( $input-context/root/UnionType/* )
return $test