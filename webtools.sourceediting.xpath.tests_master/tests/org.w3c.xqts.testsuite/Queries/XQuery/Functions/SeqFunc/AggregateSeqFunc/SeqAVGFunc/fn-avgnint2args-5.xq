(:*******************************************************:)
(:Test: avgnint2args-5                                    :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:47 GMT-05:00 2004                :)
(:Purpose: Evaluates The "avg" function                  :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:negativeInteger(lower bound)                :)
(:$arg2 = xs:negativeInteger(upper bound)                :)
(:*******************************************************:)

fn:avg((xs:negativeInteger("-999999999999999999"),xs:negativeInteger("-1")))