(:*******************************************************:) 
(: Test: fn-subsequence-mix-args-007.xq       :) 
(: Written By: Pulkita Tyagi                             :) 
(: Date: Fri May 13 04:22:43 2005                        :) 
(: Purpose: arg1: sequence of string & integer, arg2 & arg3: integer  :) 
(:********************************************************************:) 
 
fn:subsequence( ("a", xs:integer("100"), xs:integer("-100"), "b", "c"),2,4) 
