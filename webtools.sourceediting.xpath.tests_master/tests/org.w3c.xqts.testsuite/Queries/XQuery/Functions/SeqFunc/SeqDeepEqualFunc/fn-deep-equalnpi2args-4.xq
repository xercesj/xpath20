(:*******************************************************:)
(:Test: deep-equalnpi2args-4                              :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:47 GMT-05:00 2004                :)
(:Purpose: Evaluates The "deep-equal" function           :)
(: with the arguments set as follows:                    :)
(:$parameter1 = xs:nonPositiveInteger(lower bound)       :)
(:$parameter2 = xs:nonPositiveInteger(mid range)         :)
(:*******************************************************:)

fn:deep-equal((xs:nonPositiveInteger("-999999999999999999")),(xs:nonPositiveInteger("-475688437271870490")))