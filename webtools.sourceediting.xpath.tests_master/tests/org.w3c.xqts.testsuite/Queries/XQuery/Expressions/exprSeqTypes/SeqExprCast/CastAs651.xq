(:*******************************************************:)
(:Test: CastAs651                                        :)
(:Written By: Carmelo Montanez                           :)
(:Date: July 10, 2006                                    :)
(:Purpose: Evaluates casting a string into an xs:negativeInteger   :)
(:*******************************************************:)

(: insert-start :)
declare variable $input-context external;
(: insert-end :)


xs:string(-201) cast as xs:negativeInteger