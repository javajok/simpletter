package javajok.work;

import javajok.sample.Tweet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class TimelineController {

    @RequestMapping("/timeline")
    public String timeline(Model model) {
        Tweet tweet = new Tweet();
        tweet.userId = "hogehoge";
        tweet.text = "練習用だよ";

        Tweet tweet2 = new Tweet();
        tweet2.userId = "fugafuga";
        tweet2.text = "練習用2";

        ArrayList<Object> timeline = new ArrayList<>();
        timeline.add(tweet);
        timeline.add(tweet2);

        model.addAttribute("timeline", timeline);

        return "timeline";
    }
}
