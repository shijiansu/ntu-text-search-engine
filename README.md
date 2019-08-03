
## Background
- Datset - Yelp_Dataset_Challenge
- Download URL - http://www.yelp.com/dataset_challenge/

## System Requirements

1. Windows 7 or above;
2. JDK 1.7.0.10 installed or above; please use 64bit JDK. 32bit JDK will fail.
You can use the following command to check the JDK version:
java -version
3. If you run the full dataset for this assignment, it is required to allocate around 9 GB of your hard disk space.

## How to Install and Start Up the Search Engine
The executable application is: ntu-msis-ir.jar. The .jar file you can find it under SourceCode folder.
There is no need to compile and re-package the .jar from the source code.
To start the search engine, you need to use command "java -jar".

Here is a step by step example of starting the search engine:
Step 1: Open Windows Command Prompt
1) Approach One: You may use Win Flag + r, then key in "cmd" (without the double quotes), then click OK to open Command Prompt.
2) Approach Two: You may use Win Start icon, then click "All Programs", then click "Accessories", then click "Command Prompt".

Step 2:
Assume the folder with search engine and dataset as below:
D:\ir
|-ntu-msis-ir.jar
|-dataset
    |-yelp_academic_dataset_business.json
    |-yelp_academic_dataset_review.json
*PS: We strongly recommend you to put the search engine jar, dataset, and index folders under the D: drive (not C: drive) to avoid unnecessary permission issues from the Windows OS.

A example of the command as following:
java -Xms1024m -Xmx1024m -jar D:\ir\ntu-msis-ir.jar sourcereview="D:\ir\dataset\yelp_academic_dataset_review.json" sourcebusiness="D:\ir\dataset\yelp_academic_dataset_business.json" folderindex="D:\ir\index"

The syntax of the command and the command parameters are as follows:
<<JDK Parameter - Optional>>
-Xms        	: Minimum JDK memory located in this search engine during the run time. In this example, the value is 1024m.
-Xmx        	: Maximum JDK memory located in this search engine during the run time. In this example, the value is 1024m.
* PS: If you face issue "Error: Could not create the Java Virtual Machine. Error: A fatal exception has occurred. Program will exit.", that means you do not have enough memory in your PC.
So you can set a smaller values to these parameters, such as "-Xms512m -Xmx512m", or even "-Xms256m -Xmx256m", however it will slow down the process to start up the search engine.

<<Mandatory Parameter>>
sourcereview	: location of view dataset. In this example, the value is "D:\ir\dataset\yelp_academic_dataset_review.json"
sourcerebusiness: location of business dataset. In this example, the value is "D:\ir\dataset\yelp_academic_dataset_business.json"
folderindex 	: location of folder to keep the generated indexing files. In this example, the value is "D:\ir\index".

<<Optional Parameter>>
iscreateindex   : whether to clean up the indexing folder and re-create the indexing files. Value is either true or false, default value is true.
serverport  	: web server port value. Value is Integer data type, default is 8585. If the console shows ��java.net.BindException: Address already in use: bind�� then you need to provide other value for the web server port.

A example about the command and the startup logging:
C:\Users\administrator>java -Xms1024m -Xmx1024m -jar d:\ir\ntu-msis-ir.jar sourcereview="D:\ir\dataset\yelp_academic_dataset_review.json" sourcebusiness=
D:\ir\dataset\yelp_academic_dataset_business.json" folderindex="D:\ir\index"
2015-04-06 19:05:46,459 INFO Log4j appears to be running in a Servlet environment, but there's no log4j-web module available. If you want better web
ontainer support, please add the log4j-web JAR to your web archive or server lib directory.

```shell script
2015-04-06 19:05:46:551 [INFO] - fixJettyJdkIssue...
2015-04-06 19:05:46:552 [INFO] - analyzeCommand...
2015-04-06 19:05:46:553 [INFO] - command parameter: sourcereview=D:\ir\dataset\yelp_academic_dataset_review.json
2015-04-06 19:05:46:555 [INFO] - command parameter: sourcebusiness=D:\ir\dataset\yelp_academic_dataset_business.json
2015-04-06 19:05:46:555 [INFO] - command parameter: folderindex=D:\ir\index
2015-04-06 19:05:46:556 [INFO] - validateSourceFiles...
2015-04-06 19:05:46:558 [INFO] - startupWebServer...
2015-04-06 19:05:46:598 [INFO] - WebServlet() instancing...
2015-04-06 19:05:46.617:INFO:oejs.Server:jetty-8.y.z-SNAPSHOT
2015-04-06 19:05:46.874:INFO:oejs.AbstractConnector:Started SelectChannelConnector@0.0.0.0:8585
2015-04-06 19:05:46:875 [INFO] - clearIndexFolder...
2015-04-06 19:05:46:877 [INFO] - buildBusinessIndex...
2015-04-06 19:05:46:882 [INFO] - case=true, stop=true, stem=true
2015-04-06 19:05:46:997 [INFO] - index for: D:\ir\dataset\yelp_academic_dataset_business.json
2015-04-06 19:05:51:555 [INFO] - time cost indexing (ms): 4558
skipped the log for creating other index files
2015-04-06 19:32:30:385 [INFO] - ***********************************************************
2015-04-06 19:32:30:386 [INFO] - *             	<<APPLICATION STARTED>>             	*
2015-04-06 19:32:30:386 [INFO] - * ECLIPSE_RUNNING_MODE=false
2015-04-06 19:32:30:387 [INFO] - * TEST_MODE=false
2015-04-06 19:32:30:389 [INFO] - * PATH_SOURCE_REVIEW=D:\ir\dataset\yelp_academic_dataset_review.json
2015-04-06 19:32:30:390 [INFO] - * PATH_SOURCE_BUSINESS=D:\ir\dataset\yelp_academic_dataset_business.json
2015-04-06 19:32:30:391 [INFO] - * PATH_INDEX_ROOT=D:\ir\index
2015-04-06 19:32:30:392 [INFO] - * IS_CREATE_INDEX=true
2015-04-06 19:32:30:393 [INFO] - * JETTY_PORT=8585
2015-04-06 19:32:30:394 [INFO] - ***********************************************************
```

The startup of the search engine takes around 27 minutes in the following experimental environment:
Windows edition: Windows 7 Professional Service Pack 1
System Processor: Intel(R) Core(TM) i7-3520M CPU @ 2.90GHz
System Installed memory (RAM): 8.00 GB
System type: 64-bit Operating System
JDK:
java version "1.7.0_71"
Java(TM) SE Runtime Environment (build 1.7.0_71-b14)
Java HotSpot(TM) 64-Bit Server VM (build 24.71-b01, mixed mode)

Below lines are the indicator to show that the search engine is started successfully:
```shell script
***********************************************************
*             	<<APPLICATION STARTED>>             	*
... ...
***********************************************************

After the search engine starts, the folder structure of the search engine, dataset and indexing files will be as below:
D:\ir
|-ntu-msis-ir.jar
|-dataset
    |-yelp_academic_dataset_business.json
    |-yelp_academic_dataset_review.json
|-index
    |-index_business_caseN_stopN_stemN // indexing file for yelp_academic_dataset_business.json, indexing files are inside the folder.
    |-index_business_caseN_stopN_stemY
    |-index_business_caseN_stopY_stemN
    |-index_business_caseN_stopY_stemY
    |-index_business_caseY_stopN_stemN
    |-index_business_caseY_stopN_stemY
    |-index_business_caseY_stopY_stemN
    |-index_business_caseY_stopY_stemY
    |-index_review_caseN_stopN_stemN // indexing file for yelp_academic_dataset_review.json, indexing files are inside the folder.
    |-index_review_caseN_stopN_stemY
    |-index_review_caseN_stopY_stemN
    |-index_review_caseN_stopY_stemY
    |-index_review_caseY_stopN_stemN
    |-index_review_caseY_stopN_stemY
    |-index_review_caseY_stopY_stemN
    |-index_review_caseY_stopY_stemY
```

## Access the Web User Interface
By default, here are the URL you can use:
// search input page: in this page you can perform the search by clicking "Search" button, or you can click "Reset" link to clean the search result.
http://localhost:8585/ntu-msis-ir
or
http://localhost:8585/ntu-msis-ir/index.jsp

// some testing data
http://localhost:8585/ntu-msis-ir/index_test.jsp

## Logging
Log file is at %user_home%/ntu-msis-ir/info.log

## Compile and Re-Package the Application (Optional)
Java is a cross operation system language, so you can use ntu-msis-ir.jar at Windows platform or Linux platform if you installed the JDK.
However, if you want to compile and re-package the application form the source code, you need to follow the below steps.
Step 1: install Maven into your computer.
URL for download and installation instructions: https://maven.apache.org/download.cgi

Step 2: use command to the source code folder and execute Maven command
Here is a example:
```shell script
D:\ir>cd D:\ir\source\ntu-msis-ir
D:\ir\source\ntu-msis-ir>mvn clean package
... ... // downloading the dependency jar from Internet
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 01:13 min
[INFO] Finished at: 2015-04-08T11:50:11+08:00
[INFO] Final Memory: 33M/1235M
[INFO] ------------------------------------------------------------------------
```

Step 3: find your package Java jar, it is under the target folder of the source code location.
Assume the source code location is: 
    D:\ir>cd D:\ir\source\ntu-msis-ir
So the location of the jar is:
    F:\ir\source\ntu-msis-ir\target\ntu-msis-ir.jar

## Appendix - List of Open Source Libraries
By right all the dependency libraries are downloaded by Maven automatically.
It is no need to download the dependency libraries by URL one by one.

Here is a list for reference.
1) Lucene Libraries:    To provide the main query function for the most of operations of the search engine.
                            lucene-core-5.0.0.jar, lucene-queryparser-5.0.0.jar, lucene-analyzers-common-5.0.0.jar, lucene-join-5.0.0.jar
2) Java EE Libraries:   To provide web browser user interface function.
                            jstl-1.2.jar, jsp-api-2.2.jar (only uses at compiling time)
3) Jetty Libraries:     To provide embedded web server which can support the web browser user interface.
                            jetty-server-8.1.9.v20130131.jar, jetty-webapp-8.1.9.v20130131.jar, jetty-jsp-8.1.9.v20130131.jar
4) Google Libraries:    To provide the JSON format file parsing function to read the JSON format dataset files.
                            json-simple-1.1.jar
5) Utility Libraries:   To provide the utility functions for the search engine such as system logging.
                            commons-io-2.4.jar, spring-core-4.1.6.RELEASE.jar, log4j-api-2.2.jar, log4j-core-2.2.jar

## How to start at Eclipse
Run at Eclipse on MainTest.java
The change on the JSP can refresh without re-boot

// search input page, default entry, same as index.jsp
http://localhost:8585/ntu-msis-ir

// search input page
http://localhost:8585/ntu-msis-ir/index.jsp

// page with test data
http://localhost:8585/ntu-msis-ir/index_test.jsp

// search result page
http://localhost:8585/ntu-msis-ir/search
