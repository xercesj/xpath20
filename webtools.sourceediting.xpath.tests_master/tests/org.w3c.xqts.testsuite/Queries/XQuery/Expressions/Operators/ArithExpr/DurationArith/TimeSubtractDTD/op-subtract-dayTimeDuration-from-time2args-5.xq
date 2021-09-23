(:*******************************************************:)
(:Test: op-subtract-dayTimeDuration-from-time2args-5      :)
(:Written By: Carmelo Montanez                            :)
(:Date: Tue Apr 12 16:29:08 GMT-05:00 2005                :)
(:Purpose: Evaluates The "op:subtract-dayTimeDuration-from-time" operator:)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:time(lower bound)                           :)
(:$arg2 = xs:dayTimeDuration(upper bound)               :)
(:*******************************************************:)

xs:time("00:00:00Z") - xs:dayTimeDuration("P31DT23H59M59S")