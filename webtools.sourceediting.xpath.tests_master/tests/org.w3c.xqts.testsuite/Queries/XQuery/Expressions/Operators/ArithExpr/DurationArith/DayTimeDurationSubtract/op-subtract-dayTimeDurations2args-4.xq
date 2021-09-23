(:*******************************************************:)
(:Test: op-subtract-dayTimeDurations2args-4               :)
(:Written By: Carmelo Montanez                            :)
(:Date: Tue Apr 12 16:29:08 GMT-05:00 2005                :)
(:Purpose: Evaluates The "op:subtract-dayTimeDurations" operator:)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:dayTimeDuration(lower bound)               :)
(:$arg2 = xs:dayTimeDuration(mid range)                 :)
(:*******************************************************:)

xs:dayTimeDuration("P0DT0H0M0S") - xs:dayTimeDuration("P15DT11H59M59S")