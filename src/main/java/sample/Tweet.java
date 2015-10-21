package sample;

/**
 * @author irof
 */
public class Tweet {

    public String id;
    public String text;
    public String timestamp;
    public String userId;

    @Override
    public String toString() {
        return "Tweet{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
