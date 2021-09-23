(:*******************************************************:)
(:Test: op-add-dayTimeDuration-to-time-4                 :)
(:Written By: Carmelo Montanez                           :)
(:Date: July 1, 2005                                     :)
(:Purpose: Evaluates The "add-dayTimeDuration-to-time" function that  :)
(:return true and used together with fn:not.             :)
(: Uses the "fn:string" function to account for new EBV rules. :)
(:*******************************************************:)
 
fn:not(fn:string(xs:time("13:12:00Z") + xs:dayTimeDuration("P02DT07H01M")))