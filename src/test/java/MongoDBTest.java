import com.mongodb.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.net.UnknownHostException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MongoDBTest {

    private DBCollection collection;

    @Before
    public void setUp() throws Exception {
        //MockitoAnnotations.initMocks(this);
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        DB database = mongoClient.getDB("mongodb");
        collection = database.getCollection("test");
    }

    @Test
    public void A_ConnectoToMongoDB_removeAll(){
        DBCursor cursor = collection.find();
        while (cursor.hasNext()){
            DBObject o = cursor.next();
            System.out.println("Removing " + o.toString());
            collection.remove(o);
        }
    }

    @Test
    public void B_insertDirectly(){
        //Insert directly
        DBObject person = new BasicDBObject("_id", "hb1")
                .append("name", "Harout Bezdjian")
                .append("job", "Developer")
                .append("address", new BasicDBObject("street", "Terapivägen")
                        .append("city", "Huddinge")
                        .append("phone", "7979879"));
        collection.insert(person);
    }

    @Test
    public void C_insertAsPersonObject(){
        //Insert as Person object
        Address address = new Address("Sveavägen 20", "Stockholm", "1234567");
        Person person1 = new Person("hhb", "Harout Svea", "Utvecklare", address);

        DBObject personDB = Functions.toDBObject(person1);
        collection.insert(personDB);

        person1.setId("hhb1");
        DBObject personDB1 = Functions.toDBObject(person1);
        collection.insert(personDB1);
    }

    @Test
    public void D_queryOneObject(){
        //Query 1 object
        BasicDBObject query = new BasicDBObject("_id", "hhb");
        DBCursor cursor = collection.find(query);
        DBObject p = cursor.one();
        System.out.println("P: " + p.toString());
        System.out.println("P Name: " + p.get("name"));
    }

    @Test
    public void E_querySubDocuments(){
        //Query subDocs (Address)
        DBObject findSvea = new BasicDBObject("address.city", "Stockholm");
        DBCursor findSveaCursor = collection.find(findSvea);
        System.out.println("\n\nfind Svea: " + findSveaCursor.one().toString());
    }

    @Ignore
    @Test
    public void F_updateObject(){
        //Update
        DBCursor findCursor = collection.find(new BasicDBObject("address.city", "Stockholm"));
        DBObject updateHbb = findCursor.one();
        updateHbb.put("name", "Harout Updated 2000");
        collection.update(new BasicDBObject("_id", "hhb"), updateHbb);
        System.out.println("\n\nfind Svea: " + findCursor.one().toString());
    }

    @Test
    public void G_queryList(){
        //Query list
        DBCursor all = collection.find();
        System.out.println("\n\nALL Size: " + all.size());
        for(DBObject res : all){
            System.out.println("Loop-RES: " + res.toString());
        }
    }

}