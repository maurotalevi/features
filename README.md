Features Example
================

An example demonstrating a feature toggles in action in distributed webapp architecture, using: 

- Togglz for feature management
- CXF for WS
- Spring for DI 
- JBehave for BDD automated testing

To run, end to end stories

mvn clean install -Pacceptance

To run single webapps:

$ cd acceptance/ws; mvn tomcat6:run-war
$ cd acceptance/ps; mvn tomcat6:run-war


- 
