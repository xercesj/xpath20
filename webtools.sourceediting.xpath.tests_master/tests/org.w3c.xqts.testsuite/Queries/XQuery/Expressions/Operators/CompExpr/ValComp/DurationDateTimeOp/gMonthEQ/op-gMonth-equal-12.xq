(:*******************************************************:)
(:Test: op-gMonth-equal-12                               :)
(:Written By: Carmelo Montanez                           :)
(:Date: June 15, 2005                                    :)
(:Purpose: Evaluates The "gMonth-equal" function used    :)
(:together with "fn:true"/or expression (ne operator).   :)
(:*******************************************************:)
 
(xs:gMonth("--08Z") ne xs:gMonth("--07Z")) or (fn:true())