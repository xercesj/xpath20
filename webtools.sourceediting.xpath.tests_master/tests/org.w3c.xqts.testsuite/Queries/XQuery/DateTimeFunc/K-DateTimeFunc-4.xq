(:*******************************************************:)
(: Test: K-DateTimeFunc-4                                :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:39+02:00                       :)
(: Purpose: Passing the empty sequence as second argument is allowed(recent change in the specification). :)
(:*******************************************************:)
empty(dateTime(xs:date("2004-03-04"), ()))