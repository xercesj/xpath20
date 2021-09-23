(:*******************************************************:)
(:Test: minutes-from-duration1args-2                      :)
(:Written By: Carmelo Montanez                            :)
(:Date: Wed Apr 13 09:47:37 GMT-05:00 2005                :)
(:Purpose: Evaluates The "minutes-from-duration" function:)
(: with the arguments set as follows:                    :)
(:$arg = xs:dayTimeDuration(mid range)                  :)
(:*******************************************************:)

fn:minutes-from-duration(xs:dayTimeDuration("P15DT11H59M59S"))