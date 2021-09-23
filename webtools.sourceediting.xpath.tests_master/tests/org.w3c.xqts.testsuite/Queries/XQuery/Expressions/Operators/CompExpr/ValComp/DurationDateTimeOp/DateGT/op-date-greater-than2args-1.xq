(:*******************************************************:)
(:Test: op-date-greater-than2args-1                       :)
(:Written By: Carmelo Montanez                            :)
(:Date: Tue Apr 12 16:29:07 GMT-05:00 2005                :)
(:Purpose: Evaluates The "op:date-greater-than" operator :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:date(lower bound)                           :)
(:$arg2 = xs:date(lower bound)                           :)
(:*******************************************************:)

xs:date("1970-01-01Z") gt xs:date("1970-01-01Z")