(:*******************************************************:)
(:Test: op-add-dayTimeDuration-to-time-13                :)
(:Written By: Carmelo Montanez                           :)
(:Date: July 1, 2005                                     :)
(:Purpose: Evaluates The "add-dayTimeDuration-to-time" function used  :)
(:together with the numeric-equal-operator "eq".         :)
(:*******************************************************:)
 
(xs:time("01:03:03Z") + xs:dayTimeDuration("P23DT11H11M")) eq xs:time("04:03:05Z")