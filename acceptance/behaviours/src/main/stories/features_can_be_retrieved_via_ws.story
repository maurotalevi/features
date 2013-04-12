Scenario: Features can be retrieved via web services configured via META-INF/features/client.properties

Given the features web service
Then the features are: 
|name|active|
|ID_1|true|
|ID_2|false|

Scenario: Features can be retrieved via web services configured explicitly 
Meta: @skip

Given the web service with WSDL url http://localhost:8080/ws/Features?wsdl, service name {http://server.ws.features.org/}Features and activation user togglz-ws-user
Then the features are: 
|name|active|
|ID_1|true|
|ID_2|false|