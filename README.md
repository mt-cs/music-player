#### Project 3: Music Player

#### Due 12/11, 11:59pm.

##### 100 points.

In this project, you'll connect together everything we've been learning about this semester. You should already have a lot of pieces in place, so if all goes well, this will be a lot of integration and tuning.

I'm not providing any starter code for you - instead, I have a set of functional requirements. 
You should feel free to use as much of your code from previous projects as you feel is helpful. 
In other words, I encourage you to re-use what you've already built, but you should also refactor and redesign where that makes sense. 

I'll grade you on: 
- completeness. Is everything implemented? 
- correctness. Is everything well-built? Are there bugs?
- Design. Does it work well? 

What to turn in:
Check all your code into your repo, along with anything else needed to make it run. Also, please add the design doc described below. 

Step 1: (10%) Identify features. 
In Lab 7, you sketched out some features and the messages that need to be sent between the client and server in order to implement these features. Select at least three features (apart from login and showAllSongs) that you want to implement. Please send me an email describing what these features are by Friday, Feb 4. 

Step 2: (20%) login. 
To start, you'll want to be able to allow your user to log in. This will allow you to keep track of what users have played. 
Your user should be able to go to a /login.html URL, which contains a form with a text entry for a username and password. When they submit these, the following should happen:
- A Request is sent to the server, with the /login URL.
- This should invoke a loginServlet. 
- The loginServlet's doPost method should open up your SQLite DB and check the provided password against what is stored. If this is a match, return the user a page with a list of all the songs in their DB. If it is not, send them back to login.html, with a message indicating that they were not successful.
- Your DB will need a User table. It should have a primary key, a username, and a password.
- The loginServlet should also set a Cookie for the Client. This cookie should indicate the client's username. It should expire once a day.

Step 3: (20%) showAllSongs.  This will list all the songs.
Your user should be able to go to a /showAllSongs URL. When they do so:
- A request is sent to the server, which invokes the showAllSongs' doGet servlet.
- The servlet should check the cookie to make sure the user is logged in.
- If they are, the servlet should then fetch all the songs out of the SQLite DB and return then in a list or table. (you can decide how to format this.)
- If they are not logged in, send them to the login.html page.
- In order to make this work, you will need to add a Songs table to your DB. It should contain a primary key, a Text name, a foreign key for Album and a foreign key for Artist.
- You will also need tables for Artist and Album. Artist should contain a primary key and a name. Album should contain a primary key, a name, and a foreign key for Artist.
- If you want to add other fields or tables, that's fine.
- You should modify the Song, Artist and Album classes so that
 they can be easily read from and written to your DB. One way to do this is by adding new constructors and a toSQL method (akin to toString). 

Step 4. (40%) Add your features. 
These might include:
- Searching by artist.
- Building a playlist. (you will want another table for this.) 
- Searching by album.
- Adding album artwork. (this should be stored as a blob.) 
- Sorting.
- Sharing with friends. Allow one user to share a song with a friend. When the second user logs in, she should see a list of songs that have been shared, and who shared them with her. (this will require a SharedSongs table)
- Whatever else you can think of.

Step 5. (10%) Documentation.
Prepare a document (as PDF, please) that describes your application. It should include:
- A description of how to run and test your code
- a description of each of the features you implemented, both from:
	- A user perspective: how does it work? What is the user's experience?
	- A developer perspective: how is it implemented? 
- The sequence diagram from lab 7, extended as necessary

Step 6. Web services. 
Note: this step is optional extra credit, worth 20 points, applied to the final exam. (Basically, you can choose to do this instead of 20 points worth of questions on the final.) If you choose not to do it, that's fine.

There are many third-party services that provide data about music that are available programmatically. TheAudioDB is one of them - it provides a REST API in which the user sends a URL, and receives back JSON-formatted code.
The API is described here: https://www.theaudiodb.com/api_guide.php
You'll need to use a JSON library to decode this. 
JSON-simple (https://code.google.com/archive/p/json-simple/) is pretty easy to use.
When your servlet is rsponding to a request to display an artist or album, have it call out to The AudioDB, 
get back the description, and include that in the result you are returning. (You'll need to open a separate URLConnection, 
read the results, and then hand that string to your JSON parser.)



