(:*******************************************************:)
(: Test: K-ContextPositionFunc-25                        :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:41+02:00                       :)
(: Purpose: position() combined with a comparison operator inside a predicate. :)
(:*******************************************************:)
deep-equal((1, 2, 4), (1, 2, current-time(), 4)[position() ne 3])