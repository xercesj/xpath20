(:*******************************************************:)
(: Test: VarDecl054.xq          :)
(: Written By: Ravindranath Chennoju                     :)
(: Date: Wed Jun 29 14:28:57 2005                        :)
(: Purpose - Variable with no type definition            :)
(:*******************************************************:)

declare variable $x := (xs:boolean("true") , xs:boolean("0"), xs:integer("0")) ; 
$x
