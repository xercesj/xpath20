(:*******************************************************:)
(:Test: sumnni2args-1                                     :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:47 GMT-05:00 2004                :)
(:Purpose: Evaluates The "sum" function                  :)
(: with the arguments set as follows:                    :)
(:$arg = xs:nonNegativeInteger(lower bound)              :)
(:$zero = xs:nonNegativeInteger(lower bound)             :)
(:*******************************************************:)

fn:sum((xs:nonNegativeInteger("0"),xs:nonNegativeInteger("0")))