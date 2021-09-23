(:*******************************************************:)
(:Test: op-yearMonthDuration-equal2args-7                 :)
(:Written By: Carmelo Montanez                            :)
(:Date: Tue Apr 12 16:29:06 GMT-05:00 2005                :)
(:Purpose: Evaluates The "op:yearMonthDuration-equal" operator:)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:yearMonthDuration(mid range)               :)
(:$arg2 = xs:yearMonthDuration(lower bound)             :)
(:*******************************************************:)

xs:yearMonthDuration("P1000Y6M") ne xs:yearMonthDuration("P0Y0M")