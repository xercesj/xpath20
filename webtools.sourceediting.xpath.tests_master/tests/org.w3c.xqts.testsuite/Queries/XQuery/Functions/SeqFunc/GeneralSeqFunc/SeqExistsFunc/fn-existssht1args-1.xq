(:*******************************************************:)
(:Test: existssht1args-1                                  :)
(:Written By: Carmelo Montanez                            :)
(:Date: Wed Dec 15 10:51:20 GMT-05:00 2004                :)
(:Purpose: Evaluates The "exists" function               :)
(: with the arguments set as follows:                    :)
(:$arg = xs:short(lower bound)                           :)
(:*******************************************************:)

fn:exists((xs:short("-32768")))