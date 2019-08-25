import com.mongodb.*;

import java.net.UnknownHostException;

public class MongoDB {

    public static void main(String[] args) {
        try {
            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            DB database = mongoClient.getDB("mongodb");
            DBCollection collection = database.getCollection("test");

            System.out.println("collection.getCount(): " + collection.getCount());

            DBObject object = collection.findOne();
            //System.out.println("DB Object: " + object.toString());


            //Insert directly
            DBObject person = new BasicDBObject("_id", "hb1")
                    .append("name", "Harout Bezdjian")
                    .append("job", "Developer")
                    .append("address", new BasicDBObject("street", "Terapivägen")
                            .append("town", "Huddinge")
                            .append("phone", "7979879"));
            //collection.insert(person);

            //Insert as Person object
            Address address = new Address("Sveavägen 20", "Stockholm", "1234567");
            Person person1 = new Person("hhb", "Harout Svea", "Utvecklare", address);

            DBObject personDB = Functions.toDBObject(person1);
            //collection.insert(personDB);

            person1.setId("hhb1");
            DBObject personDB1 = Functions.toDBObject(person1);
            //collection.insert(personDB1);

            //Query 1 object
            BasicDBObject query = new BasicDBObject("_id", "hhb");
            DBCursor cursor = collection.find(query);
            DBObject p = cursor.one();
            System.out.println("P: " + p.toString());
            System.out.println("P Name: " + p.get("name"));

            //Query subDocs (Address)
            DBObject findSvea = new BasicDBObject("address.city", "Stockholm");
            DBCursor findSveaCursor = collection.find(findSvea);
            System.out.println("\n\nfind Svea: " + findSveaCursor.one().toString());

            //Update
            DBObject updateHbb = findSveaCursor.one();
            updateHbb.put("name", "Harout Updated 2");
            collection.update(new BasicDBObject("_id", "hhb"), updateHbb);
            System.out.println("\n\nfind Svea: " + findSveaCursor.one().toString());


            //Query list
            DBCursor all = collection.find();
            System.out.println("\n\nALL Size: " + all.size());
            for (DBObject res : all) {
                System.out.println("Loop-RES: " + res.toString());
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
