(:*******************************************************:)
(:Test: deep-equalflt2args-4                              :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:47 GMT-05:00 2004                :)
(:Purpose: Evaluates The "deep-equal" function           :)
(: with the arguments set as follows:                    :)
(:$parameter1 = xs:float(lower bound)                    :)
(:$parameter2 = xs:float(mid range)                      :)
(:*******************************************************:)

fn:deep-equal((xs:float("-3.4028235E38")),(xs:float("0")))