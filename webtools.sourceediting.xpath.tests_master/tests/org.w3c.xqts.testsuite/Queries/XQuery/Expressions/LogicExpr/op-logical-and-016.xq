(:*******************************************************:)
(: Test: op-logical-and-016.xq                           :)
(: Written By: Lalith Kumar                              :)
(: Date: Thu May 12 05:50:40 2005                        :)
(: Purpose: Logical 'and' using sequences                :)
(:*******************************************************:)

(: insert-start :)
declare variable $input-context external;
(: insert-end :)

   <return>
     { ($input-context/bib/book/price/text())
          and ($input-context/bib/book/price/text()) }
   </return>
