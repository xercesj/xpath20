(:*******************************************************:)
(:Test: sumusht3args-4                                    :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:47 GMT-05:00 2004                :)
(:Purpose: Evaluates The "sum" function                  :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:unsignedShort(lower bound)                  :)
(:$arg2 = xs:unsignedShort(mid range)                    :)
(:$zero = xs:unsignedShort(lower bound)                  :)
(:*******************************************************:)

fn:sum((xs:unsignedShort("0"),xs:unsignedShort("44633"),xs:unsignedShort("0")))