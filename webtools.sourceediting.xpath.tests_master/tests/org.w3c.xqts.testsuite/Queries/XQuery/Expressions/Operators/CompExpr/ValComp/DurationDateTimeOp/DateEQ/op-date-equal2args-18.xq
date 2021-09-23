(:*******************************************************:)
(:Test: op-date-equal2args-18                            :)
(:Written By: Carmelo Montanez                           :)
(:Date: June 3, 2005                                     :)
(:Purpose: Evaluates The "op:date-equal" operator (ge)   :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:date(upper bound)                           :)
(:$arg2 = xs:date(lower bound)                           :)
(:*******************************************************:)

xs:date("2030-12-31Z") ge xs:date("1970-01-01Z")