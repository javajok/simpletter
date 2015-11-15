package javajok.work;

import javajok.sample.Timeline;
import javajok.sample.Tweet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Controller
public class TimelineController {

    @RequestMapping("/timeline")
    public String timeline(Model model) {
        Timeline timeline = new RestTemplate().getForObject("http://localhost:8090/timeline",
                Timeline.class);

        model.addAttribute("timeline", timeline.tweets);

        return "timeline";
    }

    @RequestMapping("/tweet")
    public String tweet() {
        return "redirect:/timeline";
    }
}
