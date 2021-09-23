(:*******************************************************:)
(:Test: minnint2args-4                                    :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:47 GMT-05:00 2004                :)
(:Purpose: Evaluates The "min" function                  :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:negativeInteger(lower bound)                :)
(:$arg2 = xs:negativeInteger(mid range)                  :)
(:*******************************************************:)

fn:min((xs:negativeInteger("-999999999999999999"),xs:negativeInteger("-297014075999096793")))