(:*******************************************************:)
(:Test: concatpint2args-3                                 :)
(:Written By: Carmelo Montanez                            :)
(:Date: Wed Dec 15 15:41:48 GMT-05:00 2004                :)
(:Purpose: Evaluates The "concat" function               :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:positiveInteger(upper bound)                :)
(:$arg2 = xs:positiveInteger(lower bound)                :)
(:*******************************************************:)

fn:concat(xs:positiveInteger("999999999999999999"),xs:positiveInteger("1"))