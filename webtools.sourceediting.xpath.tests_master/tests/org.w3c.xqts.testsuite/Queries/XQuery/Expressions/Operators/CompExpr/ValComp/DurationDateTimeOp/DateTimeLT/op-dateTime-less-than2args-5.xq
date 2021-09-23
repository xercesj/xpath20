(:*******************************************************:)
(:Test: op-dateTime-less-than2args-5                      :)
(:Written By: Carmelo Montanez                            :)
(:Date: Tue Apr 12 16:29:06 GMT-05:00 2005                :)
(:Purpose: Evaluates The "op:dateTime-less-than" operator:)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:dateTime(lower bound)                       :)
(:$arg2 = xs:dateTime(upper bound)                       :)
(:*******************************************************:)

xs:dateTime("1970-01-01T00:00:00Z") lt xs:dateTime("2030-12-31T23:59:59Z")