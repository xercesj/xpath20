(:*******************************************************:)
(:Test: string-join2args-2                                :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:46 GMT-05:00 2004                :)
(:Purpose: Evaluates The "string-join" function          :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:string(mid range)                           :)
(:$arg2 = xs:string(lower bound)                         :)
(:*******************************************************:)

fn:string-join(xs:string("This is a characte"),xs:string("This is a characte"))