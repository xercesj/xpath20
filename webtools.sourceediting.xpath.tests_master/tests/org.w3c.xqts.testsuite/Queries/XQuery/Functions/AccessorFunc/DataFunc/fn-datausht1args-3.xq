(:*******************************************************:)
(:Test: datausht1args-3                                   :)
(:Written By: Carmelo Montanez                            :)
(:Date: Thu Dec 16 10:48:18 GMT-05:00 2004                :)
(:Purpose: Evaluates The "data" function                 :)
(: with the arguments set as follows:                    :)
(:$arg = xs:unsignedShort(upper bound)                   :)
(:*******************************************************:)

fn:data((xs:unsignedShort("65535")))