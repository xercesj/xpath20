(:*******************************************************:) 
(: Test: fn-subsequence-mix-args-013.xq       :) 
(: Written By: Pulkita Tyagi                             :) 
(: Date: Fri May 13 04:22:43 2005                        :) 
(: Purpose: arg1: sequence of string & double , arg2 & arg3: integer  :) 
(:********************************************************************:) 
 
fn:subsequence ( ("a", xs:double("NaN"), "b", "c"), 2, 20) 
