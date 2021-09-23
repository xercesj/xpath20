(:*******************************************************:)
(:Test: minflt2args-3                                     :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:47 GMT-05:00 2004                :)
(:Purpose: Evaluates The "min" function                  :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:float(upper bound)                          :)
(:$arg2 = xs:float(lower bound)                          :)
(:*******************************************************:)

fn:min((xs:float("3.4028235E38"),xs:float("-3.4028235E38")))