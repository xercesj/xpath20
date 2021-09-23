(:*******************************************************:)
(:Test: op-multiply-yearMonthDuration-3                  :)
(:Written By: Carmelo Montanez                           :)
(:Date: June 30, 2005                                    :)
(:Purpose: Evaluates The "multiply-yearMonthDuration" function as :)
(:part of a boolean expression (or operator) and the "fn:boolean" function. :)
(: Apply "fn:string" function to account for new EBV.     :)
(:*******************************************************:)
 
fn:string((xs:yearMonthDuration("P20Y10M") * 2.0)) or fn:false()