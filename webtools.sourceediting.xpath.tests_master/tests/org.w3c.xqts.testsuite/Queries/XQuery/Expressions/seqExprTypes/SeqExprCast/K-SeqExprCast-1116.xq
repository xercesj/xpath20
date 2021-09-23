(:*******************************************************:)
(: Test: K-SeqExprCast-1116                              :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:39+02:00                       :)
(: Purpose: Casting from xs:gDay to xs:untypedAtomic is allowed and should always succeed. :)
(:*******************************************************:)
xs:gDay("---03") cast as xs:untypedAtomic
                    ne
                  xs:untypedAtomic("an arbitrary string(untypedAtomic source)")