(:*******************************************************:)
(:Test: op-gMonth-equal2args-7                            :)
(:Written By: Carmelo Montanez                            :)
(:Date: Tue Apr 12 16:29:07 GMT-05:00 2005                :)
(:Purpose: Evaluates The "op:gMonth-equal" operator      :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:gMonth(mid range)                           :)
(:$arg2 = xs:gMonth(lower bound)                         :)
(:*******************************************************:)

xs:gMonth("--07Z") ne xs:gMonth("--01Z")