Joke Of The Day

This app supports the addition, retrieval, and removal of a joke.
It supports these actions for the current day. 
If the addition rest resource is called twice in a day, the joke 
for that day is replaced by the contents of the second call.
Maven is required to build the app and Java8 is required for execution.

To build the app:
From the root of the the unzipped file execute the following line:
mvn clean install dependency:copy-dependencies

To start the app.
From the root of the the unzipped file execute the following line:
java -cp target/dependency/*:target/elephant-0.0.1-SNAPSHOT.jar com.arb.service.SpringMain

To set up the ElephantSql data base: (again, not necessary; Utils.java shows the schema)
java -cp target/dependency/*:target/elephant-0.0.1-SNAPSHOT.jar com.arb.tools.Utils
the cloud. This database is administered as of 6/10/18. It 
is referenced in the Spring applicationContext.xml.

REST API
use a browser extension such as PostMan
Add a joke with a description:
    set header content-type to application/json
    http://localhost:8080/addJoke
    json body:  { "JokeOfTheDay": 
                   { "joke": "This is the joke text",
                     "description": "This is the desc text"
                   }
                 }
Add a joke without a description:
    set header content-type to application/json
    http://localhost:8080/addJoke
    json body:  { "JokeOfTheDay": 
                   { "joke": "This is the joke text",
                     "description": {
                        "@xsi.nil": true
                     }
                   }
                 }
                 
Retrieve today's joke:
    http://localhost:8080/joke

Delete today's joke:
    http://localhost:8080/deleteJoke
    If a joke is not present for today, the following json is returned:
    { "JokeOfTheDay": {
        "joke": "No Joke",
        "description": "No Desc"
       }
    }
    
DESIGN DETAILS:
1. A timestamp representing the start of "today" is used to determine
   if a joke is present for today. "Today" is determined by the timezone
   from which the app is executed.
2. A log file is placed in the directory from which the app is executed
   named "elephant.log" .
3. The schema for the database and the model for the rest service are
   kept separate so they can change independently of one another.
4. The rest resource "all" is for developer testing only. It will not
   render json correctly. This could be moved to another rest service to
   shield it from the user.
5. This app was developed and tested on Ubuntu (WSL on Windows 10) 
   using the Eclipse IDE.