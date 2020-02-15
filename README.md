### Microservices with Eureka ###
This branch is for learning how microservices work with Eureka Server & Client.
    <br><br>
    Go into each folder and run 
    <br>
    
    $ mvn clean install
    
and then
    
    $ java -jar target/xxxx.jar
    
Then browse http://localhost:8761 (For Eureka server) and see the services are up and running
    
And then the swagger:
    
    and http://localhost:8081/api-gateway/swagger-ui.html (To see the resources)