(:*******************************************************:)
(:Test: countnint1args-1                                  :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:47 GMT-05:00 2004                :)
(:Purpose: Evaluates The "count" function                :)
(: with the arguments set as follows:                    :)
(:$arg = xs:negativeInteger(lower bound)                 :)
(:*******************************************************:)

fn:count((xs:negativeInteger("-999999999999999999")))