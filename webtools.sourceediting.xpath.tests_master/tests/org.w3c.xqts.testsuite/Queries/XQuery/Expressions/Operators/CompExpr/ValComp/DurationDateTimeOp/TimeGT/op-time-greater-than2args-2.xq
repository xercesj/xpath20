(:*******************************************************:)
(:Test: op-time-greater-than2args-2                       :)
(:Written By: Carmelo Montanez                            :)
(:Date: Tue Apr 12 16:29:07 GMT-05:00 2005                :)
(:Purpose: Evaluates The "op:time-greater-than" operator :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:time(mid range)                             :)
(:$arg2 = xs:time(lower bound)                           :)
(:*******************************************************:)

xs:time("08:03:35Z") gt xs:time("00:00:00Z")