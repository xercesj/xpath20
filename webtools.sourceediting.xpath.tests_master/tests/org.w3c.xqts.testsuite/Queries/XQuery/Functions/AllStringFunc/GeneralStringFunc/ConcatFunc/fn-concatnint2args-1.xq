(:*******************************************************:)
(:Test: concatnint2args-1                                 :)
(:Written By: Carmelo Montanez                            :)
(:Date: Wed Dec 15 15:41:48 GMT-05:00 2004                :)
(:Purpose: Evaluates The "concat" function               :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:negativeInteger(lower bound)                :)
(:$arg2 = xs:negativeInteger(lower bound)                :)
(:*******************************************************:)

fn:concat(xs:negativeInteger("-999999999999999999"),xs:negativeInteger("-999999999999999999"))