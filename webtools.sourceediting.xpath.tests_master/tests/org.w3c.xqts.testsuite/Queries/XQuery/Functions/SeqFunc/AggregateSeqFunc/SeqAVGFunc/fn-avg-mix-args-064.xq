(:*******************************************************:)
(: Test: fn-avg-mix-args-064.xq          :)
(: Written By: Pulkita Tyagi                             :)
(: Date: Tue Nov 22 05:19:47 2005                        :)
(: Purpose: Negative test gives FORG0006                 :)
(:*******************************************************:)

fn:avg(( (xs:dateTime("1972-12-31T00:00:00"), xs:boolean("false"), (), (" ")) ))
