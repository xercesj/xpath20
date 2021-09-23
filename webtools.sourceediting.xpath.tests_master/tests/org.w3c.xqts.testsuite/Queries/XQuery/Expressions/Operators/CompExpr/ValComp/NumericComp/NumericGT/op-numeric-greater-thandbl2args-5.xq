(:*******************************************************:)
(:Test: op-numeric-greater-thandbl2args-5                 :)
(:Written By: Carmelo Montanez                            :)
(:Date: Thu Dec 16 10:48:16 GMT-05:00 2004                :)
(:Purpose: Evaluates The "op:numeric-greater-than" operator:)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:double(lower bound)                         :)
(:$arg2 = xs:double(upper bound)                         :)
(:*******************************************************:)

xs:double("-1.7976931348623157E308") gt xs:double("1.7976931348623157E308")