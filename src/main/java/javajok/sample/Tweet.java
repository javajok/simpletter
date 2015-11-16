package javajok.sample;

import java.time.LocalDateTime;

/**
 * 一件のツイートです。
 *
 * @author irof
 */
public class Tweet {

    public String id;
    public String text;
    public LocalDateTime timestamp;
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
