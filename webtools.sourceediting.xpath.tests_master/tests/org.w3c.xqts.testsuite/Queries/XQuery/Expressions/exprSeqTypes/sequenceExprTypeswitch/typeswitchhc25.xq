(: Name: typeswitchhq25 :)
(: Description: Simple test for evaluation of atomic value ("1") and return clause containing a division (idiv) operation :)
typeswitch (1)
 case $i as xs:double
    return <wrap>test failed</wrap> 
 case $i as xs:integer
   return 10 idiv 2
 case $i as xs:string
    return <wrap>test failed</wrap>
 default
   return <wrap>test failed</wrap>