(: Name: copynamespace-17 :)
(: Written by: Carmelo Montanez :)
(: Description: Evaluates copy namespace declaration with value set to "no-preserve no-inherit" .:)
(: with multiple inclusions.  Examines last element in subtree.:)

declare copy-namespaces no-preserve,no-inherit;

declare variable $element1 := <element1 xmlns:namespace1="www.namespace1.com"></element1>;
declare variable $element2 := <element2 xmlns:namespace2="www.namespace2.com">{$element1}</element2>;
declare variable $element3 := <element3 xmlns:namespace3="www.namespace3.com">{$element2}</element3>;

(: insert-start :)
declare variable $input-context1 external;
(: insert-end :)

for $var in (in-scope-prefixes($element3/element2/element1))
order by $var ascending
return $var