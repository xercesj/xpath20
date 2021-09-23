(:*******************************************************:)
(:Test: op-numeric-equalnint2args-8                       :)
(:Written By: Carmelo Montanez                            :)
(:Date: Thu Dec 16 10:48:16 GMT-05:00 2004                :)
(:Purpose: Evaluates The "op:numeric-equal" operator     :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:negativeInteger(upper bound)                :)
(:$arg2 = xs:negativeInteger(lower bound)                :)
(:*******************************************************:)

xs:negativeInteger("-1") ne xs:negativeInteger("-999999999999999999")