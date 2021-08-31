# EventManagingWebsiteSpring

Another learning project for myself based on [EventManagingSystem](https://github.com/XY-Yue/EventManagingSystem). The learning goal for this project is Java's [Spring
framework](https://spring.io/), [Thymeleaf](https://www.thymeleaf.org/) and basics of how a web app works. For a detailed explaination on the features and structure of the project, see [EventManagingSystem](https://github.com/XY-Yue/EventManagingSystem). For more details on the UI, see [EventManagingWebsite](https://github.com/XY-Yue/EventManagingWebsite).  
Currently, only the room related features are complete and available here.

## To run the project
First, download or clone the source code package.  
How I run the project: I open the project in IntelliJ IDEA and run main method in src/main/java/com/example/EventManagingWebsiteInSpring/EventManagingApplication.java.  
Then open any browser and go to [http://localhost:8080/searchRoom](http://localhost:8080/searchRoom). The project should be available there.  

## Next steps
The project is currently incomplete, but it should give a demostration of my understanding on web apps. I am not sure if I will continue with this project and finish it for 2 reasons. 
1. A new semester of university is starting.
2. My learning goal of gaining a basic understanding of web app (including sending get/post requests and handling them, responding to requests, etc.) is mostly complete.  

More importantly, if I continue to build the application now, I will probabaly be repeating the same process (sending a (async) request to the server, the server respond with some data) again and again, unlike [EventManagingWebsite](https://github.com/XY-Yue/EventManagingWebsite) where the UI part can have a lot of variations and I can gain different experiences when making different parts. I know Spring and Thymeleaf are useful tools and has a lot of features, what I have used here is just some basics of Spring MVC. Therefore, this project will continue as a learning project, and will be continued when I have a better understanding of these technologies.

## What I learned here
1. Basic Spring MVC including how to handle requests and how to include data to be displayed on the UI.
2. Basic Thymeleaf, including variables, iterations, etc.
3. Looked at Spring's dependency injection tools.
4. Sending async requests to the server using ajax on the client, and handling the response data.
