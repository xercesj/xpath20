(:*******************************************************:)
(:Test: op-date-less-than2args-3                          :)
(:Written By: Carmelo Montanez                            :)
(:Date: Tue Apr 12 16:29:07 GMT-05:00 2005                :)
(:Purpose: Evaluates The "op:date-less-than" operator    :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:date(upper bound)                           :)
(:$arg2 = xs:date(lower bound)                           :)
(:*******************************************************:)

xs:date("2030-12-31Z") lt xs:date("1970-01-01Z")