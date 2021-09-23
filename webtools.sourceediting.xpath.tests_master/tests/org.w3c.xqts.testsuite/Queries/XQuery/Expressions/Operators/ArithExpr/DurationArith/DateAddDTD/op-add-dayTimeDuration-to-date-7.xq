(:*******************************************************:)
(:Test: op-add-dayTimeDuration-to-date-7                 :)
(:Written By: Carmelo Montanez                           :)
(:Date: July 1, 2005                                     :)
(:Purpose: Evaluates The "add-dayTimeDuration-to-date" operator used  :)
(:as an argument to the "fn:string" function).           :)
(:*******************************************************:)
 
fn:string(xs:date("1989-07-05Z") + xs:dayTimeDuration("P01DT09H02M"))