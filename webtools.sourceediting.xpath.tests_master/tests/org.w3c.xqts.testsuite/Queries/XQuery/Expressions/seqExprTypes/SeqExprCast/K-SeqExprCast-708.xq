(:*******************************************************:)
(: Test: K-SeqExprCast-708                               :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:38+02:00                       :)
(: Purpose: Casting from xs:yearMonthDuration to xs:decimal isn't allowed. :)
(:*******************************************************:)
xs:yearMonthDuration("P1Y12M") cast as xs:decimal