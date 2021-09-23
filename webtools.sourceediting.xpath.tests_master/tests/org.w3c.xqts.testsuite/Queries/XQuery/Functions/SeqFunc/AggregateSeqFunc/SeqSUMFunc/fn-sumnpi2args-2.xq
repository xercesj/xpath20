(:*******************************************************:)
(:Test: sumnpi2args-2                                     :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:47 GMT-05:00 2004                :)
(:Purpose: Evaluates The "sum" function                  :)
(: with the arguments set as follows:                    :)
(:$arg = xs:nonPositiveInteger(upper bound)              :)
(:$zero = xs:nonPositiveInteger(lower bound)             :)
(:*******************************************************:)

fn:sum((xs:nonPositiveInteger("0"),xs:nonPositiveInteger("-999999999999999999")))