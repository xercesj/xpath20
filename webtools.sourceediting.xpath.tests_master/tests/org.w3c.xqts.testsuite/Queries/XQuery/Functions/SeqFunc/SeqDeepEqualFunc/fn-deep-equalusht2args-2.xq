(:*******************************************************:)
(:Test: deep-equalusht2args-2                             :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:47 GMT-05:00 2004                :)
(:Purpose: Evaluates The "deep-equal" function           :)
(: with the arguments set as follows:                    :)
(:$parameter1 = xs:unsignedShort(mid range)              :)
(:$parameter2 = xs:unsignedShort(lower bound)            :)
(:*******************************************************:)

fn:deep-equal((xs:unsignedShort("44633")),(xs:unsignedShort("0")))