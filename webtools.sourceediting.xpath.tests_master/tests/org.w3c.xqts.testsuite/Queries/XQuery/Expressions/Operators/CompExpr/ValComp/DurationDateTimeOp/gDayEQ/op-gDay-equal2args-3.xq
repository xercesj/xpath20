(:*******************************************************:)
(:Test: op-gDay-equal2args-3                              :)
(:Written By: Carmelo Montanez                            :)
(:Date: Tue Apr 12 16:29:08 GMT-05:00 2005                :)
(:Purpose: Evaluates The "op:gDay-equal" operator        :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:gDay(upper bound)                           :)
(:$arg2 = xs:gDay(lower bound)                           :)
(:*******************************************************:)

xs:gDay("---31Z") eq xs:gDay("---01Z")