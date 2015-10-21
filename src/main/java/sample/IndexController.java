package sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * @author irof
 */
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Value("${sample.api.url}")
    String apiUrl;

    @Value("${sample.userId}")
    String userId;


    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        Status[] statuses = new RestTemplate()
                .getForObject(apiUrl + "/timeline", Status[].class);
        model.addAttribute("statuses", Arrays.asList(statuses));
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String post(@RequestParam String text) {
        logger.info("post text: {}", text);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("userId", userId);
        map.add("text", text);

        Status status = new RestTemplate().postForObject(apiUrl + "/post", map, Status.class);
        logger.info(status.toString());
        return "redirect:/";
    }

    @RequestMapping("icon/{userId}")
    @ResponseBody
    public byte[] icon(@PathVariable("userId") String userId) {
        return new RestTemplate().getForObject(apiUrl + "/icon/" + userId, byte[].class);
    }
}
