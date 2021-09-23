(:*******************************************************:)
(: Test: K2-InternalVariablesWithout-1                   :)
(: Written by: Frans Englich                             :)
(: Date: 2006-08-04T17:13:26Z                            :)
(: Purpose: A variable depending on a recursive function. :)
(:*******************************************************:)

declare variable $local:myVar := local:myFunction();
declare function local:myFunction()
{
        local:myFunction(), 1, $local:myVar
};
$local:myVar