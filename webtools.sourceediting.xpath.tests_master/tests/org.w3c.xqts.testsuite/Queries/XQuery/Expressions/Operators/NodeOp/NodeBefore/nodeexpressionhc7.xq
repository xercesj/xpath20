(:*******************************************************:)
(: Test: nodeexpressionhc7                               :)
(:Written By: Carmelo Montanez                           :)
(:Date: June 8, 2005                                     :)
(:Purpose: Test of a Node before Expression used as part :)
(:of a boolean-less-than expression (lt operator).       :)
(:*******************************************************:)

(: insert-start :)
declare variable $input-context2 external;
(: insert-end :)

(($input-context2/staff[1]/employee[1]/empnum[1]) << ($input-context2/staff[1]/employee[1]/empnum[1])) lt (($input-context2/staff[1]/employee[1]/empnum[1]) << ($input-context2/staff[1]/employee[1]/empnum[1]))