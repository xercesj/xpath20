(:********************************************************:)
(: Test: boundary-space-017.xq                            :)
(: Written By: Pulkita Tyagi                              :)
(: Date: Fri Jun 17 06:27:51 2005                         :)
(: Purpose: Demonstrates stripping/preserving of boundary :)
(:          spaces by element constructors during processing of the query :)
(:************************************************************************:)

declare boundary-space strip;
<A>  A   {"B"}   C   {"D  "}</A>
