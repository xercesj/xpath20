(:*******************************************************:)
(: Test: K-SeqRemoveFunc-18                              :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:40+02:00                       :)
(: Purpose: Combine fn:remove() with operator 'eq'.      :)
(:*******************************************************:)
4 eq remove((4, xs:untypedAtomic("1")), 1)