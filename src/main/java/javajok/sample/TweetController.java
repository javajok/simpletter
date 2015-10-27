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
     * タイムラインを表示する子です。ブラウザで "/sample" にアクセスするとこの子が動きます。
     *
     * @param model テンプレートが表示するときに使う情報の設定先
     * @return 表示するテンプレート
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {

        // 次の文で、API の "/timeline" を呼び出して、結果を Timeline として受け取っています。
        //
        // （説明）
        // RestTemplateクラスは、前述の処理をまとめてくれている便利なクラスです。
        // Springでは、同じように便利クラスの XxxTemplate が用途別に用意されているので、興味があれば調べてみてください。
        // RestTemplateは主に (1)HTTPによる送受信 と (2)メッセージのバインディング の二つの仕事をします。
        // (1) HTTPの送受信はSpringのHttpClientRequestを使用してHTTPの送受信を行っていますが、
        //     ここはあまり気にしなくても良いです。通信に手を加えたいときは見てあげてください。
        // (2) メッセージのバインディングには、JSONライブラリのJacksonを使用しています。
        //     メッセージの変換に手を加えたくなった場合は、Jacksonを調べると良いでしょう。
        Timeline timeline = new RestTemplate()
                .getForObject(apiUrl + "/timeline", Timeline.class);

        // 取得した Timeline を画面に表示するために、Modelに預けます。
        //
        // （説明）
        // ModelはSpringMVCが用意しているクラスで、Controller（このクラス）と
        // テンプレート（ここではhtml）の橋渡しをしてくれます。
        // addAttributeメソッドはオーバーロードされていますが、ここで使用している引数が一つのものは、
        // 引数が二つのものの省略形で、渡したインスタンスのクラスを見て属性名を勝手につけてくれます。
        // つまり、以下の文は model.addAttribute("timeline", timeline); と同等です。
        model.addAttribute(timeline);

        // 処理が終わった際に遷移するテンプレートを指定します。
        // 以下の指定で "templates/sample/timeline.html" が表示されることになります。
        // このサンプルではテンプレートエンジンに Thymeleaf を使用しています。
        // 使っているものによってテンプレートファイルの置き場所やファイル名などは変わってきますが、
        // Controllerの実装に大きな差はないように作られています。
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
