(:*******************************************************:)
(:Test: op-boolean-equal-7                               :)
(:Written By: Carmelo Montanez                           :)
(:Date: June 24, 2005                                    :)
(:Purpose: Evaluates The "boolean-equal" function        :)
(: with operands set to "fn:not(false)" and "fn:not(false)" :)
(: respectively.                                         :)
(:*******************************************************:)
 
fn:not(xs:boolean("false")) eq fn:not(xs:boolean("false"))