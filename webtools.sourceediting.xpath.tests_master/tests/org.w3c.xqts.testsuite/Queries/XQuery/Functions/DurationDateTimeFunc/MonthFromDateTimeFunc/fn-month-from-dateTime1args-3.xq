(:*******************************************************:)
(:Test: month-from-dateTime1args-3                        :)
(:Written By: Carmelo Montanez                            :)
(:Date: Wed Apr 13 10:06:31 GMT-05:00 2005                :)
(:Purpose: Evaluates The "month-from-dateTime" function  :)
(: with the arguments set as follows:                    :)
(:$arg = xs:dateTime(upper bound)                        :)
(:*******************************************************:)

fn:month-from-dateTime(xs:dateTime("2030-12-31T23:59:59Z"))