(:*******************************************************:)
(: Test: K-CombinedErrorCodes-4                          :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:36+02:00                       :)
(: Purpose: Schema import binding to no namespace, but has three location hints. :)
(:*******************************************************:)
import(::)schema(::)"http://example.com/NSNOTRECOGNIZED"(::)at(::)"http://example.com/DOESNOTEXIST",
		(::)"http://example.com/2", "http://example.com/3"; 1 eq 1
	