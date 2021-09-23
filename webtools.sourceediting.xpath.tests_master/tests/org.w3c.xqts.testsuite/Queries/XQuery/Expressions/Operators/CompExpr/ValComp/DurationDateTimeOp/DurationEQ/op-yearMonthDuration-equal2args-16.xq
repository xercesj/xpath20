(:*******************************************************:)
(:Test: op-yearMonthDuration-equal2args-16               :)
(:Written By: Carmelo Montanez                           :)
(:Date: June 3, 2005                                     :)
(:Purpose: Evaluates The "op:yearMonthDuration-equal" operator (ge) :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:yearMonthDuration(lower bound)             :)
(:$arg2 = xs:yearMonthDuration(lower bound)             :)
(:*******************************************************:)

xs:yearMonthDuration("P0Y0M") ge xs:yearMonthDuration("P0Y0M")