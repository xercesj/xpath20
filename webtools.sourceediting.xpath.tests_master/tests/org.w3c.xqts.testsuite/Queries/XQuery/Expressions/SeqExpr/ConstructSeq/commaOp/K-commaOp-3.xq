(:*******************************************************:)
(: Test: K-commaOp-3                                     :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:36+02:00                       :)
(: Purpose: An expression sequence containing many empty sequences and one xs:string. On some implementations this triggers certain optimization paths. :)
(:*******************************************************:)
((), (), ((), (), ((), (), ("str")), ()), (), (())) eq "str"