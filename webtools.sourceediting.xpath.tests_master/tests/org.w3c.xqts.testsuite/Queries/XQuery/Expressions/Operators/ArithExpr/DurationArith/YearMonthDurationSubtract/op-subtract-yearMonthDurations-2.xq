(:*******************************************************:)
(:Test: op-subtract-yearMonthDurations-2                 :)
(:Written By: Carmelo Montanez                           :)
(:Date: June 28, 2005                                    :)
(:Purpose: Evaluates The "subtract-yearMonthDurations" function :)
(:used as part of a boolean expression (and operator) and the "fn:false" function. :)
(: Apply "fn:string" function to account for new EBV.     :)
(:*******************************************************:)

fn:string(xs:yearMonthDuration("P10Y11M") - xs:yearMonthDuration("P12Y07M")) and fn:false()