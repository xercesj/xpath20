(:*******************************************************:)
(:Test: countdbl1args-3                                   :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:47 GMT-05:00 2004                :)
(:Purpose: Evaluates The "count" function                :)
(: with the arguments set as follows:                    :)
(:$arg = xs:double(upper bound)                          :)
(:*******************************************************:)

fn:count((xs:double("1.7976931348623157E308")))