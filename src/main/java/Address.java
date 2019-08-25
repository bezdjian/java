import com.mongodb.BasicDBObject;

public class Address extends BasicDBObject {

    private String street;
    private String city;
    private String phone;

    public Address(String street, String city, String phone) {
        this.street = street;
        this.city = city;
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
