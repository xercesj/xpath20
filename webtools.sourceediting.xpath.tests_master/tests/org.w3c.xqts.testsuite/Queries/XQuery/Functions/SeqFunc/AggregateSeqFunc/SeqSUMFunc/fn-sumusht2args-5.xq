(:*******************************************************:)
(:Test: sumusht2args-5                                    :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:47 GMT-05:00 2004                :)
(:Purpose: Evaluates The "sum" function                  :)
(: with the arguments set as follows:                    :)
(:$arg = xs:unsignedShort(lower bound)                   :)
(:$zero = xs:unsignedShort(upper bound)                  :)
(:*******************************************************:)

fn:sum((xs:unsignedShort("0"),xs:unsignedShort("65535")))