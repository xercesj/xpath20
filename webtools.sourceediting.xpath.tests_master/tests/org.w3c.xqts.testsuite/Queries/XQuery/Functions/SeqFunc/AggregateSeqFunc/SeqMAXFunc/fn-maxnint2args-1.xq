(:*******************************************************:)
(:Test: maxnint2args-1                                    :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:47 GMT-05:00 2004                :)
(:Purpose: Evaluates The "max" function                  :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:negativeInteger(lower bound)                :)
(:$arg2 = xs:negativeInteger(lower bound)                :)
(:*******************************************************:)

fn:max((xs:negativeInteger("-999999999999999999"),xs:negativeInteger("-999999999999999999")))