(:*******************************************************:)
(:Test: sumintg2args-2                                    :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:47 GMT-05:00 2004                :)
(:Purpose: Evaluates The "sum" function                  :)
(: with the arguments set as follows:                    :)
(:$arg = xs:integer(upper bound)                         :)
(:$zero = xs:integer(lower bound)                        :)
(:*******************************************************:)

fn:sum((xs:integer("999999999999999999"),xs:integer("-999999999999999999")))