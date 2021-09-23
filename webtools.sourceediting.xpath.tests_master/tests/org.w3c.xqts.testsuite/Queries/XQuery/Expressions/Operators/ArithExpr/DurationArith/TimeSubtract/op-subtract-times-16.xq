(:*******************************************************:)
(:Test: op-subtract-times-16                             :)
(:Written By: Carmelo Montanez                           :)
(:Date: June 28, 2005                                    :)
(:Purpose: Evaluates The "subtract-times" function used  :)
(:together with the numeric-equal operator "ge".         :)
(:*******************************************************:)
 
(xs:time("13:00:00Z") - xs:time("17:00:00Z")) ge xs:dayTimeDuration("P17DT10H02M")