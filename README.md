#Sellics Amazon SEO Estimation Demo Application

This application's main assumption is when we search with Amazon autocomplete when
typing each letter in the keyword the we have to find exact keyword in the suggestions.
If we can not find estimation score is zero. We can say that the weak point of this 
assumption is we are not try to find if any suggestion contains the keyword we are trying to
find exact match. We can not say that containing keyword has effects on seo score, there is no 
information about this. And depending on there not enough info about that we can ignore results 
that contains keywords.

##Estimation Logic

We can try to explain estimation algorithm step by step 

**Step 1**

* For estimating seo score for keyword, first we create subsequence sets that
contains words in keyword like :

>  keyword = iphone  
>  subsequence  list = ["i","ip","iph","ipho","iphon","iphone"]


**Step 2** 

* Then we use Amazon autocomplete service for every item in the list. To make 
aplication fast we make every request parallel and we collect the results 
(e.g AmazonKeywordResult class). Now we have search results for every words 
in the subsequence list

**Step 3**

* For our estimation score we try to calculate estimation score unit for keyword. 
FOr score unit point we divide 100 to keyword length.  (e.g. medianValueForLetter)

>  keyword = iphone  
>  estimation score unit point = 100 / (length of keyword)  
>  for "iphone" keyword score unit point = 100 / 5 = 20.


**Step 4**
* In this step we try to find keyword in the search result list for every subsequence 
word. If we can not find in the any search result then estimation score is 0 (zero), if we can 
find we check which search subsequence word's results is contains. 

**Step 5**

* Lastly when we find which subsequence word, which has minimum length, search results 
contains keyword we calculate estimation score as :

> 100 - (estimationScoreUnitPoint * (subsequenceWordLength - 1))  
> For example if find "iphone" keyword on the search result that "iph" subsequence word
then estimation score for "iphone" keyword is 100 - (20 * (3 - 1)) ) = 60

 
