(:*******************************************************:)
(:Test: op-dayTimeDuration-less-than-9                   :)
(:Written By: Carmelo Montanez                           :)
(:Date: June 15, 2005                                    :)
(:Purpose: Evaluates The "dayTimeDuration-less-than" function used:)
(:together with "or" expression (lt operator).           :)
(:*******************************************************:)
 
(xs:dayTimeDuration("P10DT08H") lt xs:dayTimeDuration("P10DT07H")) or (xs:dayTimeDuration("P10DT09H") lt xs:dayTimeDuration("P10DT09H"))