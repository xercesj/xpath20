(:*******************************************************:)
(: Test: K-SeqExprCast-1305                              :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:39+02:00                       :)
(: Purpose: 'castable as' involving xs:base64Binary as source type and xs:gMonth as target type should always evaluate to false. :)
(:*******************************************************:)
not(xs:base64Binary("aaaa") castable as xs:gMonth)