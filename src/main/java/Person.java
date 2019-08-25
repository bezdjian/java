import com.mongodb.BasicDBObject;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Person extends BasicDBObject {

    private String id;
    private String name;
    private String job;

    private Address address;
}
