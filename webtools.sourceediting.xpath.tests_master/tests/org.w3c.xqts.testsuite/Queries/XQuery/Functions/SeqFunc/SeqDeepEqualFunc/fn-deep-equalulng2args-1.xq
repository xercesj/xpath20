(:*******************************************************:)
(:Test: deep-equalulng2args-1                             :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:47 GMT-05:00 2004                :)
(:Purpose: Evaluates The "deep-equal" function           :)
(: with the arguments set as follows:                    :)
(:$parameter1 = xs:unsignedLong(lower bound)             :)
(:$parameter2 = xs:unsignedLong(lower bound)             :)
(:*******************************************************:)

fn:deep-equal((xs:unsignedLong("0")),(xs:unsignedLong("0")))