(:*******************************************************:)
(:Test: op-add-dayTimeDuration-to-date2args-1             :)
(:Written By: Carmelo Montanez                            :)
(:Date: Tue Apr 12 16:29:08 GMT-05:00 2005                :)
(:Purpose: Evaluates The "op:add-dayTimeDuration-to-date" operator:)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:date(lower bound)                           :)
(:$arg2 = xs:dayTimeDuration(lower bound)               :)
(:*******************************************************:)

xs:date("1970-01-01Z") + xs:dayTimeDuration("P0DT0H0M0S")