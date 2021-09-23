(:*******************************************************:)
(:Test: avgsht2args-5                                     :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:47 GMT-05:00 2004                :)
(:Purpose: Evaluates The "avg" function                  :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:short(lower bound)                          :)
(:$arg2 = xs:short(upper bound)                          :)
(:*******************************************************:)

fn:avg((xs:short("-32768"),xs:short("32767")))