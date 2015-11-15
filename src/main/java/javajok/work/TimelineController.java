package javajok.work;

import javajok.sample.Tweet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TimelineController {

    @RequestMapping("/timeline")
    public String timeline(Model model) {
        Tweet tweet = new Tweet();
        tweet.userId = "hogehoge";
        tweet.text = "練習用だよ";

        model.addAttribute("tweet", tweet);

        return "timeline";
    }
}
