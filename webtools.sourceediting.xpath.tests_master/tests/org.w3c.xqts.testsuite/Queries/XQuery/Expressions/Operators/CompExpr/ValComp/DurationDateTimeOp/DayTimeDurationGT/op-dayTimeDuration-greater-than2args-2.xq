(:*******************************************************:)
(:Test: op-dayTimeDuration-greater-than2args-2            :)
(:Written By: Carmelo Montanez                            :)
(:Date: Tue Apr 12 16:29:06 GMT-05:00 2005                :)
(:Purpose: Evaluates The "op:dayTimeDuration-greater-than" operator:)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:dayTimeDuration(mid range)                 :)
(:$arg2 = xs:dayTimeDuration(lower bound)               :)
(:*******************************************************:)

xs:dayTimeDuration("P15DT11H59M59S") gt xs:dayTimeDuration("P0DT0H0M0S")