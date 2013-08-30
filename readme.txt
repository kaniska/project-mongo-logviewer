                                                                     
                                                                     
                                                                     
                                             
Flow :
store files in a folder -> import into MongoDB -> view in UI 

(1)start mongoDB
> B:\mongodb\bin>mongod --dbpath c:/TEMP/
(2)start mongoViewer
> B:\mongoViewer>start_mViewer.bat
Access the Tool
> http://localhost:8080/index.html

1.::: db.vzlogs.find({"device":"SCH-I405"})
1.::  db.vzlogs.findOne({exception: /^j/}, { "exception": 1 })
1. db.vzlogs.find({exception: /^j/})
db.vzlogs.find(

Implemantation Approachs :

(1) Simply use import tool :
Goal : import json file into mongo db and query using mongo viewer 
(i) Create a new db 'test' using mViewer
(ii) Create a new Collection vzlogs 
(ii)  Open mongo shell :
B:\mongodb\bin>mongo
> use test
B:\mongodb\bin>
> mongoimport -d test --upsert --upsertFields report_id -c vzlogs --type json --file C:\logviewer\data\abc.json




(2) Programmatically extract document from json file 
>> 

Other Info :

Download MongoViewer : 
https://github.com/downloads/Imaginea/mViewer/mViewer-v0.9.1.zip

>>>>>>>
Flow :
ETL : Extract documents from Json Lines : JsonLineItemReader
        Transform documents into prpoer structure
        Load documents in MongoDB
   
  MongoDbItemWriter
  > http://blog.codecentric.de/en/2012/11/spring-batch-mongodb/

Analyze MongoDB data :
(1) simple query : mongoViewer
(2) use jasperSoft to create charts out of mongodb data : http://community.jaspersoft.com/wiki/jaspersoft-mongodb-web-analytics-sample
 
(3) http://wiki.pentaho.com/display/BAD/Create+a+Parameterized+Report+with+MongoDB

(4) http://www.qvsource.com/wiki/MongoDB-Connector.ashx#Running_Queries_0
=======================

Advanced Notes :
MongoDB Reference :
http://samarthbhargava.wordpress.com/2012/02/01/real-time-analytics-with-mongodb/


Spring-batch : 
http://stackoverflow.com/questions/8955363/dynamically-choose-a-spring-batch-reader-at-runtime

Spring MVC + JQuery
http://krams915.blogspot.com/search/label/JSON

jasperSOft :  Related resources :  http://community.jaspersoft.com/wiki/mongo-db-passing-param-chart
        :http://community.jaspersoft.com/search?query=mongodb&page=1

http://blog.mongohq.com/blog/2013/01/22/first-week-with-mongodb-2-dot-4-development-release


http://stackoverflow.com/questions/12694490/how-do-i-use-aggregation-operators-in-a-match-in-mongodb-for-example-year-or

;-------------

grep "Exception" acra.log

1. exception 4.0.5 start dtae 
2. exception  " ----" 1.0.5 stack trace,model, app start time ,crash time ,android version , app version
 3. device  not found from a date 

active state phyton 2.7  

1. Opened a conversation
2. Using search ,I wanted to search a string " 2 Members"  which belongs more than one time on that conversation.
3. It showed me 2 results and hisgligthed the latest one that has received/sent out.
4. Pressed up arrow to view the previous match but its not working 
So could not test the down arrow feature.

1. Wanted to search a string "Resting" which has multiple entry.
2. On search screen typed  only  "R" , not the exact word , but it was showing hints" Resting"  and time stamp.
3. Chosen "Resting" from there. 
On CL Its highlighting the actual word but showing 1 results for "R" . could not able to figure out that I specefied " Resting" on search screen
///////

http://stackoverflow.com/questions/12878722/mongodb-nested-field-in-groups-id

http://docs.mongodb.org/ecosystem/tutorial/getting-started-with-java-driver/

http://stackoverflow.com/questions/15564562/how-to-write-multiple-group-by-id-fields-in-mongodb-java-driver

http://stackoverflow.com/questions/14312910/mongodb-java-driver-hide-id-field-in-aggregation-projection-operation

http://docs.mongodb.org/manual/tutorial/aggregation-with-user-preference-data/

http://docs.mongodb.org/manual/tutorial/aggregation-zip-code-data-set/

http://docs.mongodb.org/manual/reference/method/db.collection.group/

http://stackoverflow.com/questions/15696802/mongodb-aggregation-framework-group-by-two-fields

http://stackoverflow.com/questions/11418985/mongodb-aggregation-framework-group-over-multiple-values
http://stackoverflow.com/questions/10116350/mongodb-how-to-do-queries-on-multiple-attributes-within-an-object-and-group-th
http://docs.mongodb.org/manual/reference/aggregation/project/

http://docs.mongodb.org/manual/reference/aggregation/unwind/

https://groups.google.com/forum/#!topic/mongodb-user/wwox47Zio2E
http://stackoverflow.com/questions/13073770/mongodb-aggregate-on-subdocument-in-array

http://stackoverflow.com/questions/10891805/how-to-return-partial-array-match-in-inner-array-of-mongodb

http://stackoverflow.com/questions/17221949/how-to-make-a-partial-queries-in-mongodb

http://stackoverflow.com/questions/6179871/mongodb-wildcard-in-the-key-of-a-query

http://stackoverflow.com/questions/14501337/mongodb-aggregation-framework-match-between-fields?rq=1

http://www.nodans.com/index.cfm/2013/7/23/Using-MongoDB-Aggregation-Framework-to-Filter-Trello-Cards

http://docs.mongodb.org/manual/reference/operator/regex/#op._S_regex

=====================================================================

