(:*******************************************************:)
(:Test: contains2args-3                                   :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:47 GMT-05:00 2004                :)
(:Purpose: Evaluates The "contains" function             :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:string(upper bound)                         :)
(:$arg2 = xs:string(lower bound)                         :)
(:*******************************************************:)

fn:contains(xs:string("This is a characte"),xs:string("This is a characte"))