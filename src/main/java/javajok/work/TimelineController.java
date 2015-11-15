package javajok.work;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TimelineController {

    @RequestMapping("/timeline")
    public String timeline(Model model) {
        return "timeline";
    }
}
