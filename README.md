# Java ThreadPooling File Server


## Getting Started
This project is File Server that is capable to handle CRUD requests (GET, POST, PUT, DELETE) and do operations on files that
are present on the server. <br>
The client should build the request with: <br> 
```
PATH: <SERVER_IP>:<SERVER_PORT>/<FILE_PATH> 
METHOD: GET | POST | PUT | DELETE
KEEP_ALIVE: YES/NO </b>(Keep Alive behaviour was implemented with setSocketTimeout(10 seconds))
```
In this moment the concurency of paralel request processing threads is implemented using ThreadPoolExecutor that have 10 Threads.
In order to organize thread access to files I implemented a ConcurrentHashMap where I mark when a file is used or not
in order to access a resource without having the problem that another thread is doing operations on it.

### Prerequisites
<b> For Developers: </b> To write this project I used IntellijIDEA but any IDE that support Java should be fine <br>
<b> For Users: </b>Install a version of Java that is >= Java 8 (JDK) <br>

### Installing
In order to run the server you need to:
```
(compile) in root directory of the project: mvn clean install 
(run) java -cp target/fileserver-1.0-SNAPSHOT.jar Main <SERVER_PORT> <SERVER_HOME>
<SERVER_PORT> The port that will be used by the server (check if that port is used by another process)
<SERVER_HOME> Filepath of the directory where the clients will have access to write or get files
```
Right now you need to provide the absolute path in order to work: <br>
<b> eg: java -cp target/fileserver-1.0-SNAPSHOT.jar Main 5000 C:\Users\user\Desktop\fileServer-master\target </b>



## Running the tests

This project was tested using a python service that is present in <b> /src/test/testApi.py </b> <br>
This test service should be run after the server was started with arguments:<br>
<b> <SERVER_IP> <SERVER_PORT> </b> and <b> <SERVER_HOME> </b> <br>
The purpose of it is to check if the requests are processed well by the server, files are returned, created <br>
Until now I tested the server only on Windows environment using Google Chrome browser and Postman application <br>
In order to run the testing service you need to install Python (I tested with 2.7 version) and requests python module <br>
```
To run the test service: python /src/test/testApy.py <SERVER_IP> <SERVER_PORT> <SERVER_HOME>
eg: python /src/test/testApi.py localhost 5000 C:\Users\user\Desktop\fileServerHomework
```


## Deployment
Docker setup is present in /dockerContainer <br>
In order to work you need to update this with your new SERVER_HOME absolute path </br>

Built With
Intellij IDEA - IDE
Maven - Dependency Management

## Versioning
I used github as repository and git bash as versioning tool

## Authors
Costin-Valeriu Costea

## License
OpenSource

## Sources
http://tutorials.jenkov.com/java-multithreaded-servers/thread-pooled-server.html <br>
https://gist.github.com/vagmi/7168756 <br>
https://stackoverflow.com/questions/35983807/java-httpserver-handling-post-requests-and-read-the-html-form-information <br>
https://dzone.com/articles/run-simple-jar-application-in-docker-container-1 <br>
https://github.com/marius-avram/http-thread-pooling-server <br>

<b> Fingers Crossed! :) </b>
