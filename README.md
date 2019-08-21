### Microservices with Eureka ###
This branch is for learning how microservices work with Eureka Server & Client.
<p>
    Go into each folder and run 
    <br>
    ```mvn clean install```
    <br> and <br>
    ```java -jar target/xxxx.jar```
    <br>
    Then browse http://localhost:8761 (For Eureka server) <br>
    and http://localhost:8088/items (To see the items from 'itemcatalog' service
    <br>
    and http://localhost:8089/top-brands (To see results from edge-service)
</p>