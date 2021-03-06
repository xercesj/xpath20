(:*******************************************************:)
(:Test: sumnint3args-2                                    :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:47 GMT-05:00 2004                :)
(:Purpose: Evaluates The "sum" function                  :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:negativeInteger(upper bound)                :)
(:$arg2 = xs:negativeInteger(lower bound)                :)
(:$zero = xs:negativeInteger(lower bound)                :)
(:*******************************************************:)

fn:sum((xs:negativeInteger("-1"),xs:negativeInteger("-999999999999999999"),xs:negativeInteger("-999999999999999999")))