(:*******************************************************:)
(:Test: adjust-dateTime-to-timezone-18                   :)
(:Written By: Carmelo Montanez                           :)
(:Date: August 10, 2005                                  :)
(:Test Description: Evaluates The "adjust-dateTime-to-timezone" function   :)
(:as part of a subtraction expression, whicg results on a negative number.:)
(:Uses one adjust-dateTime-to-timezone function and one :)
(:xs:dateTime constructor.                               :)
(:*******************************************************:)

fn:adjust-dateTime-to-timezone(xs:dateTime("2002-03-07T10:00:00-04:00")) - xs:dateTime("2006-03-07T10:00:00-05:00")