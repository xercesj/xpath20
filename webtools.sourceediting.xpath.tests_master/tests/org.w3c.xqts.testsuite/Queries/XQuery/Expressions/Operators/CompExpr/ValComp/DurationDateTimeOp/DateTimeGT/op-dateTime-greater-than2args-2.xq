(:*******************************************************:)
(:Test: op-dateTime-greater-than2args-2                   :)
(:Written By: Carmelo Montanez                            :)
(:Date: Tue Apr 12 16:29:06 GMT-05:00 2005                :)
(:Purpose: Evaluates The "op:dateTime-greater-than" operator:)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:dateTime(mid range)                         :)
(:$arg2 = xs:dateTime(lower bound)                       :)
(:*******************************************************:)

xs:dateTime("1996-04-07T01:40:52Z") gt xs:dateTime("1970-01-01T00:00:00Z")