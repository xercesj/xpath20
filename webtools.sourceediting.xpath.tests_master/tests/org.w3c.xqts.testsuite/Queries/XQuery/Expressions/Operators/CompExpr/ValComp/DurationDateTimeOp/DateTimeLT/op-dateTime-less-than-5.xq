(:*******************************************************:)
(:Test: op-dateTime-less-than-5                          :)
(:Written By: Carmelo Montanez                           :)
(:Date: June 15, 2005                                    :)
(:Purpose: Evaluates The "dateTime-less-than" function that  :)
(:return false and used together with fn:not (lt operator):)
(:*******************************************************:)
 
fn:not(xs:dateTime("2002-05-02T12:00:00Z") lt xs:dateTime("2002-04-02T12:00:00Z"))