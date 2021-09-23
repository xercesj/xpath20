(:*******************************************************:)
(:Test: op-date-equal2args-15                            :)
(:Written By: Carmelo Montanez                           :)
(:Date: June 3, 2005                                     :)
(:Purpose: Evaluates The "op:date-equal" operator (le)   :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:date(lower bound)                           :)
(:$arg2 = xs:date(upper bound)                           :)
(:*******************************************************:)

xs:date("1970-01-01Z") le xs:date("2030-12-31Z")