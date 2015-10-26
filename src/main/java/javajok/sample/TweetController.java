package javajok.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author irof
 */
@Controller
@RequestMapping("sample")
public class TweetController {

    private static final Logger logger = LoggerFactory.getLogger(TweetController.class);

    @Value("${javajok.api.url}")
    String apiUrl;

    @Value("${javajok.userId}")
    String userId;

    /**
     * タイムラインを表示する子です。
     * APIの "/timeline" を呼び出して、取得できた {@link Timeline} を設定してテンプレートを表示させます。
     *
     * @param model テンプレートが表示するときに使う情報の設定先
     * @return 表示するテンプレート
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        Timeline timeline = new RestTemplate()
                .getForObject(apiUrl + "/timeline", Timeline.class);
        model.addAttribute(timeline);
        return "sample/timeline";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String tweet(@RequestParam String text) {
        logger.info("tweet text: {}", text);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("userId", userId);
        map.add("text", text);

        Tweet tweet = new RestTemplate().postForObject(apiUrl + "/tweet", map, Tweet.class);
        logger.info(tweet.toString());
        return "redirect:sample";
    }

    @RequestMapping("icon/{userId}")
    @ResponseBody
    public byte[] icon(@PathVariable("userId") String userId) {
        return new RestTemplate().getForObject(apiUrl + "/icon/" + userId, byte[].class);
    }
}
