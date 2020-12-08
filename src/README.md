### Lab 7: Web Applications

#### 30 points

#### due Sunday November 22, 11:59pm.



task 1: (10 points) Client side: processing URLs.

The URLTester class demonstrates how to open an HTTPS connection and consume the incoming data. It uses Weather Underground as a source.
Modify it to use an address near you, and use the code you've built previously (either streams, or the Processor, or anything else 
you like) to remove all the HTML tags and other codes.
 

task 2: (10 points) Designing your app.
In this task, you'll sketch out the components of your music player and their interaction. 
Create a diagram that helps you determine the answers to these questions:

1. Name at least three tasks that your user might perform. For each task, what screens or pages will they see?
2. What is the interaction between the client and server for each task? What URL does the client use? What servlet does this map to?
What does the servlet return? What happens if there is an error? What if a task requires more than one request/response?

task 3: (10 points) servlets.

To begin, download [jetty](https://www.eclipse.org/jetty/). It will unpack into a directory called jetty-distribution*. 
If this is your first Java application of this style, take a little time to think about where you want to put it.

You will need to add jetty/lib to your project's libraries in order to use jetty.
Get the provided code running, and verify that you are able to access your server by fetching the URL localhost:8081 (or whatever port 
you are using) from your web browser.

Then create a servlet for each of the request/response pairs in your diagram.  Have the responses be static HTML read from files. 
If you are familiar with HTML, you are welcome to make these look pretty. If you're new to HTML [W3Schools](https://www.w3schools.com/) 
is a good starting point - just return a basic page that lets you know it's working.

You should be able to run your servlet on your own machine, open a web browser to the correct port, and page through the application. 
