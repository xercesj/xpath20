(:*******************************************************:)
(: Test: K-LogicExpr-13                                  :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:37+02:00                       :)
(: Purpose: Non-empty xs:untypedAtomics in the right branch of an or-expression has an EBV value of true. :)
(:*******************************************************:)
0 or xs:untypedAtomic("a string")