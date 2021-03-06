(: insert-start :)
declare variable $input-context external;
(: insert-end :)

<result>
 {
   for $i in $input-context//item_tuple
   where $i/start_date <= xs:date("1999-01-31")
     and $i/end_date >= xs:date("1999-01-31")
     and contains($i/description, "Bicycle")
   order by $i/itemno
   return
       <item_tuple>
           { $i/itemno }
           { $i/description }
       </item_tuple>
 }
</result>