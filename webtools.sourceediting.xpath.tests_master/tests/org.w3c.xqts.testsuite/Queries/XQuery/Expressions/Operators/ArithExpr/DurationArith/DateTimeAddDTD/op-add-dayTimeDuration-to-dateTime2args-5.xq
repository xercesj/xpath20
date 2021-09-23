(:*******************************************************:)
(:Test: op-add-dayTimeDuration-to-dateTime2args-5         :)
(:Written By: Carmelo Montanez                            :)
(:Date: Tue Apr 12 16:29:08 GMT-05:00 2005                :)
(:Purpose: Evaluates The "op:add-dayTimeDuration-to-dateTime" operator:)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:dateTime(lower bound)                       :)
(:$arg2 = xs:dayTimeDuration(upper bound)               :)
(:*******************************************************:)

xs:dateTime("1970-01-01T00:00:00Z") + xs:dayTimeDuration("P31DT23H59M59S")