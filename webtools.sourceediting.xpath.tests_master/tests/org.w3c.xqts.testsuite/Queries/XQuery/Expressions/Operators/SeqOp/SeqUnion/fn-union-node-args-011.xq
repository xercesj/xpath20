(:*******************************************************:)
(: Test: fn-union-node-args-011.xq          :)
(: Written By: Pulkita Tyagi                             :)
(: Date: Tue May 24 03:34:54 2005                        :)
(: Purpose: arg: comment node & node                     :)
(:*******************************************************:)

(: insert-start :)
declare variable $input-context external;
(: insert-end :)

$input-context/comment() | $input-context/bib/book[1]/title
