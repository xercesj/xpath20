(:*******************************************************:)
(:Test: op-gYear-equal2args-9                             :)
(:Written By: Carmelo Montanez                            :)
(:Date: Tue Apr 12 16:29:07 GMT-05:00 2005                :)
(:Purpose: Evaluates The "op:gYear-equal" operator       :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:gYear(lower bound)                          :)
(:$arg2 = xs:gYear(mid range)                            :)
(:*******************************************************:)

xs:gYear("1970Z") ne xs:gYear("2012Z")