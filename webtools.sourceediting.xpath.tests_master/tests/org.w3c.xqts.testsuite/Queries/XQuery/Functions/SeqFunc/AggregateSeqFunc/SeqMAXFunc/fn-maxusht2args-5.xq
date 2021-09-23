(:*******************************************************:)
(:Test: maxusht2args-5                                    :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:47 GMT-05:00 2004                :)
(:Purpose: Evaluates The "max" function                  :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:unsignedShort(lower bound)                  :)
(:$arg2 = xs:unsignedShort(upper bound)                  :)
(:*******************************************************:)

fn:max((xs:unsignedShort("0"),xs:unsignedShort("65535")))