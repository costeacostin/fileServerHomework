# fileServer
Adobe homework project

the purpose to do file system operations using web interface.

In order to start this you need to open the project with your IDE (I used Intellij to develop)
Right now 4 HTTP methods are implemented: GET, POST, PUT, DELETE
GET:
POST:
PUT:
DELETE:

to run:
in the root directory: mvn clean install
java -cp target/fileserver-1.0-SNAPSHOT.jar Main <port> <serverhome>
ex: java -cp target/fileserver-1.0-SNAPSHOT.jar Main 5000 C:\Users\ccostea\Desktop\fileServer-master\target
  
  
  
Project Title
Java ThreadPooling File Server

Getting Started
This project is File Server that is capable to handle CRUD requests (GET, POST, PUT, DELETE) and do operations on files that
are present on the server.
The client should build the request with:
PATH: <SERVER_IP>:<SERVER_PORT>/<FILE_PATH>
METHOD: GET | POST | PUT | DELETE
KEEP_ALIVE: YES/NO
In this moment the concurency of paralel request processing threads is implemented using ThreadPoolExecutor that have 10 Threads.
In order to organize thread access to files I implemented a ConcurrentHashMap where I mark when a file is used or not
in order to access a resource without having the problem that another thread is doing operations on it.

Prerequisites
For Developers: To write this project I used IntellijIDEA but any IDE that support Java should be fine
For Users: Install a version of Java that is >= Java 8 (JDK)
In order to run the server you need to:
in root directory of the project: mvn clean install 
java -cp target/fileserver-1.0-SNAPSHOT.jar Main <PORT> <SERVER_HOME>
<PORT> The port that will be used by the server (check if that port is used by another process)
<SERVER_HOME> Filepath of the directory where the clients will have access to write or get files
 
eg: java -cp target/fileserver-1.0-SNAPSHOT.jar Main 5000 C:\Users\user\Desktop\fileServer-master\target



Running the tests
This project was tested using a python service that is present in /src/test/testApi.py
This test service should be run after the server was started with <PORT> and <SERVER_HOME> arguments
The purpose of it is to check if the requests are processed well by the server, files are returned, created
Until now I tested the server only on Windows environment using Google Chrome browser and Postman application
In order to run the testing service you need to install Python (I tested with 2.7 version) and requests python module

eg: python /src/test/testApi.py 5000 C:\Users\user\Desktop\fileServerHomework


Deployment
Add additional notes about how to deploy this on a live system

Built With
Dropwizard - The web framework used
Maven - Dependency Management
ROME - Used to generate RSS Feeds

Contributing
Please read CONTRIBUTING.md for details on our code of conduct, and the process for submitting pull requests to us.

Versioning
We use SemVer for versioning. For the versions available, see the tags on this repository.

Authors
Costin-Valeriu Costea
See also the list of contributors who participated in this project.

License
This project is licensed under the MIT License - see the LICENSE.md file for details

Acknowledgments
Hat tip to anyone whose code was used
Inspiration
etc
