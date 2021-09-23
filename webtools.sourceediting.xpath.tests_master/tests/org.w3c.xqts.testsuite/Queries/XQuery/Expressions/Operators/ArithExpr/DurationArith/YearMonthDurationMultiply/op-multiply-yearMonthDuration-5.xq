(:*******************************************************:)
(:Test: op-multiply-yearMonthDuration-5                  :)
(:Written By: Carmelo Montanez                           :)
(:Date: June 30, 2005                                    :)
(:Purpose: Evaluates The "multiply-yearMonthDuration" function that  :)
(:is used as an argument to the fn:boolean function.     :)
(: Apply "fn:string" function to account for new EBV.     :)
(:*******************************************************:)
 
fn:boolean(fn:string(xs:yearMonthDuration("P05Y08M") * 2.0))