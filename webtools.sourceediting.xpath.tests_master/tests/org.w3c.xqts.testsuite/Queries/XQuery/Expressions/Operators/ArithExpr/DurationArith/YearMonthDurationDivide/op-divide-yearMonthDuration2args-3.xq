(:*******************************************************:)
(:Test: op-divide-yearMonthDuration2args-3                :)
(:Written By: Carmelo Montanez                            :)
(:Date: Tue Apr 12 16:29:08 GMT-05:00 2005                :)
(:Purpose: Evaluates The "op:divide-yearMonthDuration" operator:)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:yearMonthDuration(upper bound)             :)
(:$arg2 = xs:double(lower bound)                         :)
(:*******************************************************:)

xs:yearMonthDuration("P2030Y12M") div xs:double("-1.7976931348623157E308")