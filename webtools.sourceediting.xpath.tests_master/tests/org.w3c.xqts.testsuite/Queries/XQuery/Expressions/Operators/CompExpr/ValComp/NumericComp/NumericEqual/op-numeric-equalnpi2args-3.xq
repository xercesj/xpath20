(:*******************************************************:)
(:Test: op-numeric-equalnpi2args-3                        :)
(:Written By: Carmelo Montanez                            :)
(:Date: Thu Dec 16 10:48:16 GMT-05:00 2004                :)
(:Purpose: Evaluates The "op:numeric-equal" operator     :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:nonPositiveInteger(upper bound)             :)
(:$arg2 = xs:nonPositiveInteger(lower bound)             :)
(:*******************************************************:)

xs:nonPositiveInteger("0") eq xs:nonPositiveInteger("-999999999999999999")