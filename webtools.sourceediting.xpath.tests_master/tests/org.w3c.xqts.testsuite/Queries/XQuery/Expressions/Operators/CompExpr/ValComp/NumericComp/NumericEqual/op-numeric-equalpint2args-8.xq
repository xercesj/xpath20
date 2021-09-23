(:*******************************************************:)
(:Test: op-numeric-equalpint2args-8                       :)
(:Written By: Carmelo Montanez                            :)
(:Date: Thu Dec 16 10:48:16 GMT-05:00 2004                :)
(:Purpose: Evaluates The "op:numeric-equal" operator     :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:positiveInteger(upper bound)                :)
(:$arg2 = xs:positiveInteger(lower bound)                :)
(:*******************************************************:)

xs:positiveInteger("999999999999999999") ne xs:positiveInteger("1")