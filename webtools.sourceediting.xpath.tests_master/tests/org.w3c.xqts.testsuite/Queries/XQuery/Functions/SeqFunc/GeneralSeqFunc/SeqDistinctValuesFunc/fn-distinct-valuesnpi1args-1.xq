(:*******************************************************:)
(:Test: distinct-valuesnpi1args-1                         :)
(:Written By: Carmelo Montanez                            :)
(:Date: Wed Dec 15 11:37:32 GMT-05:00 2004                :)
(:Purpose: Evaluates The "distinct-values" function      :)
(: with the arguments set as follows:                    :)
(:$arg = xs:nonPositiveInteger(lower bound)              :)
(:*******************************************************:)

fn:distinct-values((xs:nonPositiveInteger("-999999999999999999")))