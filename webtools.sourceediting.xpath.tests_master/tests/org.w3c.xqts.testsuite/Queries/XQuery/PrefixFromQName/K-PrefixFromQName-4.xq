(:*******************************************************:)
(: Test: K-PrefixFromQName-4                             :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:40+02:00                       :)
(: Purpose: A test whose essence is: `prefix-from-QName( QName("example.com/", "pre:lname")) eq "pre"`. :)
(:*******************************************************:)
prefix-from-QName(
			QName("example.com/", "pre:lname")) eq "pre"