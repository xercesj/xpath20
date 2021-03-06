(:*******************************************************:)
(: Test: K-SeqMINFunc-23                                 :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:41+02:00                       :)
(: Purpose: A type error must be reported even if one of other values is NaN. :)
(:*******************************************************:)
max((xs:float("NaN"), 1, xs:untypedAtomic("one")))