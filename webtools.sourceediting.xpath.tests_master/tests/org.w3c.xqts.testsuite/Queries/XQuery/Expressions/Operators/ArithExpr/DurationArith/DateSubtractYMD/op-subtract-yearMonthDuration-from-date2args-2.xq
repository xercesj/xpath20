(:*******************************************************:)
(:Test: op-subtract-yearMonthDuration-from-date2args-2    :)
(:Written By: Carmelo Montanez                            :)
(:Date: Tue Apr 12 16:29:08 GMT-05:00 2005                :)
(:Purpose: Evaluates The "op:subtract-yearMonthDuration-from-date" operator:)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:date(mid range)                             :)
(:$arg2 = xs:yearMonthDuration(lower bound)             :)
(:*******************************************************:)

xs:date("1983-11-17Z") - xs:yearMonthDuration("P0Y0M")