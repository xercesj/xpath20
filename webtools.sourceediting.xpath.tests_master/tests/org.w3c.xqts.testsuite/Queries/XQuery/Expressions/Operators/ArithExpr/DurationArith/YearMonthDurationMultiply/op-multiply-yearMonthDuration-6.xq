(:*******************************************************:)
(:Test: op-multiply-yearMonthDuration-6                  :)
(:Written By: Carmelo Montanez                           :)
(:Date: June 30, 2005                                    :)
(:Purpose: Evaluates The "multiply-yearMonthDuration" operator that :)
(:is used as an argument to the fn:number function.      :)
(:*******************************************************:)
 
fn:number(xs:yearMonthDuration("P02Y09M") * 2.0)