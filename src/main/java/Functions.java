import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Functions {
    public static DBObject toDBObject(Person person) {
        return new BasicDBObject("_id", person.getId())
                .append("name", person.getName())
                .append("job", person.getJob())
                .append("address", new BasicDBObject("street", person.getAddress().getStreet())
                        .append("city", person.getAddress().getCity())
                        .append("phone", person.getAddress().getPhone()));
    }
}
