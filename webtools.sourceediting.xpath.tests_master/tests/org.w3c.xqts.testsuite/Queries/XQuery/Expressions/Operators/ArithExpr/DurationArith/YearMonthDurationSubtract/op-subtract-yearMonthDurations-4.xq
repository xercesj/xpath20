(:*******************************************************:)
(:Test: op-subtract-yearMonthDurations-4                 :)
(:Written By: Carmelo Montanez                           :)
(:Date: June 28, 2005                                    :)
(:Purpose: Evaluates The "subtract-yearMonthDurations" function that  :)
(:return true and used together with fn:not.             :)
(: Apply "fn:string" function to account for new EBV.     :)
(:*******************************************************:)
 
fn:not(fn:string(xs:yearMonthDuration("P11Y04M") - xs:yearMonthDuration("P02Y11M")))