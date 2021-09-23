(:*******************************************************:)
(:Test: op-subtract-yearMonthDurations2args-3             :)
(:Written By: Carmelo Montanez                            :)
(:Date: Tue Apr 12 16:29:08 GMT-05:00 2005                :)
(:Purpose: Evaluates The "op:subtract-yearMonthDurations" operator:)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:yearMonthDuration(upper bound)             :)
(:$arg2 = xs:yearMonthDuration(lower bound)             :)
(:*******************************************************:)

xs:yearMonthDuration("P2030Y12M") - xs:yearMonthDuration("P0Y0M")