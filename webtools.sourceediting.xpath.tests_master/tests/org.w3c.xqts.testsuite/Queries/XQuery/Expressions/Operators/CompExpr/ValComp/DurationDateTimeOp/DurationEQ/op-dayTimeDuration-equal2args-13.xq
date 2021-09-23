(:*******************************************************:)
(:Test: op-dayTimeDuration-equal2args-13                 :)
(:Written By: Carmelo Montanez                           :)
(:Date: June, 3 2005                                     :)
(:Purpose: Evaluates The "op:dayTimeDuration-equal" operator (le) :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:dayTimeDuration(upper bound)               :)
(:$arg2 = xs:dayTimeDuration(lower bound)               :)
(:*******************************************************:)

xs:dayTimeDuration("P31DT23H59M59S") le xs:dayTimeDuration("P0DT0H0M0S")