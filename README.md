### Microservices with Eureka ###
This branch is for learning how microservices work with Eureka Server & Client.
    <br><br>
    Go into each folder and run 
    <br>
    
    $ mvn clean install
    
    and then
    
    $ java -jar target/xxxx.jar
    
    Then browse http://localhost:8761 (For Eureka server)
    
    and http://localhost:8088/items (To see the items from 'itemcatalog' service
    
    and http://localhost:8089/top-brands (To see results from itemcatalog client)