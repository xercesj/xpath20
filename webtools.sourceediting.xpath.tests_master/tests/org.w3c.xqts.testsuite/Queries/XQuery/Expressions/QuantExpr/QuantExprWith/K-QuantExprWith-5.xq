(:*******************************************************:)
(: Test: K-QuantExprWith-5                               :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:37+02:00                       :)
(: Purpose: Every-quantification carrying type declarations(cardinalities have no impact). :)
(:*******************************************************:)
every $a as item()+ in (1, 2), $b as item()+ in $a satisfies $b