(:*******************************************************:)
(: Test: K-SeqExprCast-1093                              :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:39+02:00                       :)
(: Purpose: 'castable as' involving xs:gMonthDay as source type and xs:gMonthDay as target type should always evaluate to true. :)
(:*******************************************************:)
xs:gMonthDay("--11-13") castable as xs:gMonthDay