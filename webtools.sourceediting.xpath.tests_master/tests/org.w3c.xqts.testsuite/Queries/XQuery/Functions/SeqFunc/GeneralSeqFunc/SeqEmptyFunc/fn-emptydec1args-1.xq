(:*******************************************************:)
(:Test: emptydec1args-1                                   :)
(:Written By: Carmelo Montanez                            :)
(:Date: Thu Dec 16 10:48:18 GMT-05:00 2004                :)
(:Purpose: Evaluates The "empty" function                :)
(: with the arguments set as follows:                    :)
(:$arg = xs:decimal(lower bound)                         :)
(:*******************************************************:)

fn:empty((xs:decimal("-999999999999999999")))