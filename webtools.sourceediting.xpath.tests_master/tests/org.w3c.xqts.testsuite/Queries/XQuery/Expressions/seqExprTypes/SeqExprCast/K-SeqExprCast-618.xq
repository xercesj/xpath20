(:*******************************************************:)
(: Test: K-SeqExprCast-618                               :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:38+02:00                       :)
(: Purpose: Casting from xs:integer to xs:date isn't allowed. :)
(:*******************************************************:)
xs:integer("6789") cast as xs:date