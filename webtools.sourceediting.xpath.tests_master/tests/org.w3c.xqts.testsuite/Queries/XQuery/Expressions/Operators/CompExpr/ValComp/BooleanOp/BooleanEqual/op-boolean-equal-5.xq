(:*******************************************************:)
(:Test: op-boolean-equal-5                               :)
(:Written By: Carmelo Montanez                           :)
(:Date: June 24, 2005                                    :)
(:Purpose: Evaluates The "boolean-equal" function        :)
(: with left operand set to and "and" expressions with the :)
(: "not" function and right operand set to "true".       :)
(:*******************************************************:)
 
fn:not(xs:boolean("true") and xs:boolean("true")) eq xs:boolean("true")