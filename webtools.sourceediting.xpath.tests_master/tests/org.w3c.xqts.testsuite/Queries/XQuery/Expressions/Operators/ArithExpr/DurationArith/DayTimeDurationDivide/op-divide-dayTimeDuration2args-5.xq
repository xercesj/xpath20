(:*******************************************************:)
(:Test: op-divide-dayTimeDuration2args-5                  :)
(:Written By: Carmelo Montanez                            :)
(:Date: Tue Apr 12 16:29:08 GMT-05:00 2005                :)
(:Purpose: Evaluates The "op:divide-dayTimeDuration" operator:)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:dayTimeDuration(lower bound)               :)
(:$arg2 = xs:double(upper bound)                         :)
(:*******************************************************:)

xs:dayTimeDuration("P0DT0H0M0S") div xs:double("1.7976931348623157E308")