import com.mongodb.BasicDBObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Address extends BasicDBObject {

    private String street;
    private String city;
    private String phone;
}
