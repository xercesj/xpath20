(:*******************************************************:)
(: Test: K-SeqExprCast-196                               :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:37+02:00                       :)
(: Purpose: No zone offset is allowed for xs:yearMonthDuration. :)
(:*******************************************************:)
xs:yearMonthDuration("P20Y15M+08:00")