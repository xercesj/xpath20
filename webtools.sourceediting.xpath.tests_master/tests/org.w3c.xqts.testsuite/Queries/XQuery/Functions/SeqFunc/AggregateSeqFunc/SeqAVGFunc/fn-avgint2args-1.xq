(:*******************************************************:)
(:Test: avgint2args-1                                     :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:47 GMT-05:00 2004                :)
(:Purpose: Evaluates The "avg" function                  :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:int(lower bound)                            :)
(:$arg2 = xs:int(lower bound)                            :)
(:*******************************************************:)

fn:avg((xs:int("-2147483648"),xs:int("-2147483648")))