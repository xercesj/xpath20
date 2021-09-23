(:*******************************************************:)
(:Test: op-gYearMonth-equal2args-5                        :)
(:Written By: Carmelo Montanez                            :)
(:Date: Tue Apr 12 16:29:07 GMT-05:00 2005                :)
(:Purpose: Evaluates The "op:gYearMonth-equal" operator  :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:gYearMonth(lower bound)                     :)
(:$arg2 = xs:gYearMonth(upper bound)                     :)
(:*******************************************************:)

xs:gYearMonth("1970-01Z") eq xs:gYearMonth("2030-12Z")