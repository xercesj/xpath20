(: Name: LocalNameFromQNameFunc001 :)
(: Description: Test function fn:local-name-from-QName. Using QName element as input :)

declare default element namespace "http://www.example.com/QNameXSD"; 

(: insert-start :)
declare variable $input-context external;
(: insert-end :)

fn:local-name-from-QName(fn:QName("http://www.example.com/QNameXSD", xs:string(($input-context/root/elemQN)[1]))) 
