<h2>Trafiklab API test</h2>

**Instructions**

_Installation_
<ul>
    <li>Clone or download the project</li>
    <li>Cd into project root where there is pom.xml file</li>
    <li>Install maven and edit the PATH and MAVEN_HOME</li>
    <li>Run: `mvn install && mvn spring-boot:run`</li>
</ul>

**Usage**

<ul>
    <li>
    Browse http://localhost:7777/traffic-lab/swagger-ui.html for Swagger
        - Add http://localhost:7777/traffic-lab/api-docs to Explore.   
    </li>
<li>
For YAML version: http://localhost:7777/traffic-lab/api-docs
</li>
    <li>
    Browse http://localhost:7777/traffic-lab/ for frontend with thymeleaf framework with HTML & JS
    </li>
    <li>
    **Extra**  <br>  
    There is a search field where you can type a line number and retrieve 
    the stop names of that line.
    </li>
    <li>
    There is also Cache available for the backend functions so we can cache the
    top ten list and the stop names of the line that has the most stops.
    </li>
</ul>

**Extra**

<ul>
<li>Those two functions (retrieve the top ten lines who have the most stops) and 
 (search the bus stop names by given line number) have available endpoints where
 you can make a request and get as JSON response</li>
 <li>/getMostStops</li>
 <li>/getStops/{lineNumber}</li>
</ul>