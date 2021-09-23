(:*******************************************************:)
(:Test: op-date-equal2args-10                             :)
(:Written By: Carmelo Montanez                            :)
(:Date: Tue Apr 12 16:29:07 GMT-05:00 2005                :)
(:Purpose: Evaluates The "op:date-equal" operator        :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:date(lower bound)                           :)
(:$arg2 = xs:date(upper bound)                           :)
(:*******************************************************:)

xs:date("1970-01-01Z") ne xs:date("2030-12-31Z")