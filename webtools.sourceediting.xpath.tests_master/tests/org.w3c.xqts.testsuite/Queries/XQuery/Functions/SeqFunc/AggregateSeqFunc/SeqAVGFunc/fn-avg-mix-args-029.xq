(:*******************************************************:)
(: Test: fn-avg-mix-args-029.xq          :)
(: Written By: Pulkita Tyagi                             :)
(: Date: Tue Nov 22 05:19:47 2005                        :)
(: Purpose: Negative test gives FORG0006                 :)
(:*******************************************************:)

fn:avg(( fn:empty("Hello")) or fn:boolean(fn:count("Hello")))
