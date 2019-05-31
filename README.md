<h2>Trafiklab API test</h2>

**Instructions**

_Installation_
<ul>
    <li>Run: `mvn install && mvn spring-boot:run`</li>
    <li>Browse localhost:7777/h2-console</li>
    <li>Make sure you choose Generic H2 (Embedded) in both Saved Settings and Setting Name</li>
    <li>Driver Class: org.h2.Driver</li>
    <li>JDBC URL: jdbc:h2:mem:testdb</li>
    <li>Username: sa</li>
    <li>Empty password</li>
</ul>

**Usage**

<ul>
    <li>
    During application start, two Trafiklab's APIs is called and the data is saved 
    to H2 database with 2 different tables.
    </li>
    <li>
    Browse http://localhost:7777 and the page will load once and do an Ajax call to 
    backend webservice that collects the top ten bus stops and the names 
    from the H2 database and it will cache them.
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