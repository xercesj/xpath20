(:*******************************************************:)
(:Test: op-numeric-equalnpi2args-10                       :)
(:Written By: Carmelo Montanez                            :)
(:Date: Thu Dec 16 10:48:16 GMT-05:00 2004                :)
(:Purpose: Evaluates The "op:numeric-equal" operator     :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:nonPositiveInteger(lower bound)             :)
(:$arg2 = xs:nonPositiveInteger(upper bound)             :)
(:*******************************************************:)

xs:nonPositiveInteger("-999999999999999999") ne xs:nonPositiveInteger("0")