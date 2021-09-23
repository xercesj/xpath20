(:*******************************************************:)
(:Test: op-numeric-greater-thandec2args-4                 :)
(:Written By: Carmelo Montanez                            :)
(:Date: Thu Dec 16 10:48:16 GMT-05:00 2004                :)
(:Purpose: Evaluates The "op:numeric-greater-than" operator:)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:decimal(lower bound)                        :)
(:$arg2 = xs:decimal(mid range)                          :)
(:*******************************************************:)

xs:decimal("-999999999999999999") gt xs:decimal("617375191608514839")