(:*******************************************************:)
(:Test: existsdbl1args-3                                  :)
(:Written By: Carmelo Montanez                            :)
(:Date: Wed Dec 15 10:51:20 GMT-05:00 2004                :)
(:Purpose: Evaluates The "exists" function               :)
(: with the arguments set as follows:                    :)
(:$arg = xs:double(upper bound)                          :)
(:*******************************************************:)

fn:exists((xs:double("1.7976931348623157E308")))