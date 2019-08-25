import com.mongodb.BasicDBObject;

public class Person extends BasicDBObject {

    private String id;
    private String name;
    private String job;

    private Address address;

    public Person(String id, String name, String job, Address address) {
        this.id = id;
        this.name = name;
        this.job = job;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
