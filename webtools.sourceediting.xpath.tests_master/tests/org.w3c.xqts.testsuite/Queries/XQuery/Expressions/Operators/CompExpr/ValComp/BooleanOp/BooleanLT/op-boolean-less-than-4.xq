(:*******************************************************:)
(:Test: op-boolean-less-than-4                           :)
(:Written By: Carmelo Montanez                           :)
(:Date: June 15, 2005                                    :)
(:Purpose: Evaluates The "boolean-less-than" function    :)
(: with operands set to "not(true)", "false" respectively.:)
(: Use of ge operator.                                   :)
(:*******************************************************:)
 
fn:not(xs:boolean("true")) ge xs:boolean("false")