# Namics Framework

Framework for Java servlets inspired by Spring framework.

Internship project mentored by [Stefan Dragisic](https://github.com/stefanvozd) who made tasks for the development of this project.  
Tasks can be seen at [the end](#tasks) of the document.

Internship role: AEM (Adobe Experience Manager) Engineer

## About project


Implementation of core features that AEM /Java web application relies on by creating mini AEM/Spring like framework named 'namicsFramework'.

Maven multi-module project.

Annotation ```@NamicsXmlValueMap``` injects values from XML file (map.xml) into a variable.
Reflection is able to recognize and create objects based on XML structure.  
```MainServlet``` is the main servlet of the application. Default Java servlet is hidden in the background. Classes annotated with ```@NamicsServlet``` are subscribed to HTTP requests from the ```MainServlet```.


```@NamicsServlet``` has the following fields :  
```path``` - path (URL) for servlet  
```selector``` - name of the file  
```returns``` - format of the file that the main servlet converts to and writes the content on the page (``` JSON ```, ``` XML ```)  
```method``` - HTTP request method (```GET```, ```POST```)  

If there is no servlet registered to the given path, the main servlet goes to a local path (eg. ``` ../path/index.html``` ) and gets the ``` index.html```  file and outputs file as a response.

Java8 features used: Lambda expressions, Method references, Functional Interface, Collectors class, ```ForEach()``` method, Stream API  
Other technologies used: Jetty Server, JUnit, Mockito, Log4j

### Prerequisites

[Maven 3.3.9](https://maven.apache.org/docs/3.3.9/release-notes.html)  
Download link and unzip the file.  
Add the bin directory of the created directory apache-maven-3.3.9 to the PATH environment variable.

Example  (Unix-based Operating System)

```
$ unzip apache-maven-3.3.9-bin.zip
```  
Check enviroment varibales:
```
$ echo $JAVA_HOME 
/usr/lib/jvm/java-8-oracle
```  
Export PATH:
```
$ export PATH=/opt/apache-maven-3.6.9/bin:$PATH
```

Confirm with ``` $  mvn -v``` in a new shell. The result should look similar to:
```
Apache Maven 3.3.9
Maven home: /usr/share/maven
Java version: 1.8.0_191, vendor: Oracle Corporation
Java home: /usr/lib/jvm/java-8-oracle/jre
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "4.4.0-141-generic", arch: "amd64", family: "unix"
```

### Installing

Install project by running following Maven command from root folder:
```
$ mvn clean install
```

The jar file is packed in ```namicsFramework/examples/target```. 
```
$ cd examples/target
$ ls
archive-tmp  classes  examples-1.0-SNAPSHOT.jar  examples-1.0-SNAPSHOT-jar-with-dependencies.jar  generated-sources  maven-archiver  maven-status
```  
Execute jar file by running java command :  
```
$ java -jar examples-1.0-SNAPSHOT-jar-with-dependencies.jar
```  
The output should look like this:  
```
2019-01-26 04:06:25.563:INFO::Logging to STDERR via org.mortbay.log.StdErrLog
2019-01-26 04:06:25.579:INFO::jetty-6.1.x
2019-01-26 04:06:25.680:INFO::Started SelectChannelConnector@0.0.0.0:8080
```

Now the Jetty server and the application is up and running. To stop press ``` Ctrl + C ```.

You can also start the application from IDE by running ```main``` method from ``` namicsFramework/examples/src/main/java/tests/examples/MainApp.java ```

## Tests

Tests are written for ```namicsServlet``` and ```xmlValueMap```.
Tests automatically start the server and cover basic case scenarios by simulating sending HTTP requests. Test coverage: >80%.


## Author :

* *Milana Dundic*

## Tasks 
author :  [Stefan Dragisic](https://github.com/stefanvozd)


**Content:** Some of the advanced Java features are explained in this section. All of these features are crucial to understanding
mechanisms behind modern Java web application.  
**Goal:** Goal is to understand and can independently implement core features that AEM /Java web application relies on.  
At the end of this section, intern will create mini AEM/Spring like framework named ```namicsFramework```.


*Task:* Implement small framework that support value injecting from an XML file.  
Make a maven multi-module project named namicsFramework and implement an annotation called ``` @NamicsXmlValueMap ``` that will inject values from the XML file (map.xml) into a variable.  
Example of annotation usage:  
```
@NamicsXmlValueMap
private String key1;
@NamicsXmlValueMap
private Color key2;
```  
Example of xml file:  
```
<?xml version="1.0" encoding="utf-8"?>
<entries>
<key1> value1 </key1>
<key2>
<color>
<r>234</r><g>123</g><b>99</b>
</color>
</key2>
</entries>
```  
Reflection is supposed to be able to recognize and create objects based on XML structure.  

Example of injected object:
```
@NamicsXmlValueMap
private Color key2;
class Color {
int r;
int g;
int b;
}
```


*Task:* Add new annotation ```@NamicsServlet``` to namicsFramework that will implement Java Jetty servlet.  
Default Java servlet should be hidden in the background. The annotated class should subscribe to HTTP requests from the main servlet based on annotation ```@NamicsServlet```.  
The main servlet should reroute requests to annotated classes and process response if needed.  
Fields in annotation are :  
```path``` - represents path (URL) for  servlet  
```selector``` - represents the name of the file  
```returns``` - represents the format of the file that the main  servlet will convert to and write the content on the page  
```method``` - represents the HTTP request method that can and will be used  

Example of  servlet usage:  
```
@NamicsServlet(  
 path = "/myfolder",
 selector = "myselector",
 returns = "json",
 method = "GET"  
)
class MyClass extends MyServletInterface {
 ...
 @override
 doGet(...) {
 }
}
```  
For the example above, the servlet should be called only if a user goes to URL: ``` localhost/myfolder/myselector.json ``` and
calls GET method.  


*Task:* Add the feature to ```namicsFramework``` so that if the path does not exist, the main servlet checks the local path.
For example, if the requested path is: localhost/myfolder/index.html, and there is no  servlet registered to this path,
the dispatcher should go to the local path and get the index.html file and output file the response.


*Task:* Use ```@NamicsXmlValueMap``` annotation in servlet.  
Example code:  
```
@NamicsServlet(
 path = "/myfolder",
 selectors = {
 "color"
 },
 returns = "json",
 methods = "GET",
)
class MyClass extends MyServletInterface {
    @NamicsXmlValueMap
    private Color color;
 ...
 
 @override
 doGet() {
    return color;
    }
 }
 ```
 
 
Explanation:  
localhost/myfolder/color.json gets the values from the color object which inherited the value from map.xml and output
result to response.  
Response example:  
```
{
"r" : "222",
 "g" : "211",
 "b" : "92"
}
```  
Or if the servlet is marked that it returns XML (returns = "XML") the content should automatically converted to XML
format.  


*Lesson:* Java 8 streams  
Introduction to Java 8 streams.  https://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/  
*Task:* Java 8 streams  
Modify myFramework project to Make use of Stream API.  


*Lesson:* Testing  
Introduction to Unit Testing with JUnit and Mockito.  
*Task:* Testing  
Add JUnit tests to the 'myFramework' project and archive >80% code coverage.  
