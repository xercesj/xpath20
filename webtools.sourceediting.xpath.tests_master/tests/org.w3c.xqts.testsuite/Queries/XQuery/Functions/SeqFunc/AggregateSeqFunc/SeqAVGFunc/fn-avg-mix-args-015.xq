(:*******************************************************:)
(: Test: fn-avg-mix-args-015.xq          :)
(: Written By: Pulkita Tyagi                             :)
(: Date: Tue Nov 22 05:19:47 2005                        :)
(: Purpose: arg: seq of float,decimal                    :)
(:*******************************************************:)

fn:avg(( (xs:float("-0"), xs:decimal("-999999999999999999")  ))) eq xs:float("-4.99999992E17")
