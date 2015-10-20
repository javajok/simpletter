package sample;

/**
 * @author irof
 */
public class Status {

    public String id;
    public String text;
    public String timestamp;
    public String userId;

    @Override
    public String toString() {
        return "Status{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
