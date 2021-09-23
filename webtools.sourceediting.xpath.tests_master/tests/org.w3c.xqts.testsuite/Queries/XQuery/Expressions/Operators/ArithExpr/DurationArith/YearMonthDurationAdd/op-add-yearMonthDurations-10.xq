(:*******************************************************:)
(:Test: op-add-yearMonthDurations-10                     :)
(:Written By: Carmelo Montanez                           :)
(:Date: June 29, 2005                                    :)
(:Purpose: Evaluates The "add-yearMonthDurations" function used  :)
(:together with an "or" expression.                      :)
(: Apply "fn:string" function to account for new EBV.     :)
(:*******************************************************:)
 
fn:string((xs:yearMonthDuration("P05Y02M") + xs:yearMonthDuration("P03Y04M"))) or fn:string((xs:yearMonthDuration("P05Y03M") + xs:yearMonthDuration("P01Y03M")))