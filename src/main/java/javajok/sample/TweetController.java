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
 * 画面からのリクエストを処理してAPIサーバーと通信したり、遷移先を指定したりするコントローラーです。
 *
 * Spring MVCにコントローラーであることを伝えるために<code>@Controller</code>を付与します。
 * <code>@RequestMapping</code>は指定したパスにアクセスした時に動くようにする設定です。
 * ここでは "/sample" を指定していますので、 http://localhost:8080/sample にアクセスするとこのクラスが呼び出されます。
 *
 * @author irof
 */
@Controller
@RequestMapping("/sample")
public class TweetController {

    /**
     * ログを出力するためのライブラリの準備です。
     */
    private static final Logger logger = LoggerFactory.getLogger(TweetController.class);

    /**
     * このアプリの通信先となるAPIのURLです。
     * 値は src/main/resources/application.yml に記述されています。
     * 起動時引数で <code>-Djavajok.api.url=http://hogedriven.net/simpletter</code> などを指定すると上書きできます。
     * 今回は変更が必要な場合は application.yml を編集しちゃってください。
     */
    @Value("${javajok.api.url}")
    String apiUrl;

    /**
     * このアプリで使用するユーザーIDです。
     * 値の設定については apiUrl と同じです。
     * 設定されているユーザーIDがAPIサーバーにない場合、APIサーバー側で登録を行ってください。
     */
    @Value("${javajok.userId}")
    String userId;

    /**
     * タイムラインを表示する子です。ブラウザで "/sample" を表示すると動きます。
     * <code>@RequestMapping</code> のmethod属性にはHTTPメソッドを指定します。
     * ブラウザでURLをアドレスバーに入れたり、Aタグのリンクなどで移動してきた場合はGETメソッドです。
     * また、value属性を指定していないので、classに付与しているパスが使用されます。
     *
     * @param model テンプレートが表示するときに使う情報の設定先
     * @return 表示するテンプレート
     */
    @RequestMapping(method = RequestMethod.GET)
    public String timeline(Model model) {

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

    /**
     * つぶやきを受け取る子です。ブラウザからつぶやくボタンを押したら動きます。
     *
     * @param text つぶやく内容
     * @return 処理後にやること
     */
    @RequestMapping(method = RequestMethod.POST)
    public String tweet(@RequestParam String text) {
        // つぶやく内容をログに出力します。
        logger.info("tweet text: {}", text);

        // "/tweet" のAPIが userId, text のフォームデータを欲しがっているので、組み立てます。
        // このアプリはユーザーID固定なので、フィールドの userId を設定します。
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("userId", userId);
        map.add("text", text);

        // 次の文で、APIの "/tweet" につぶやき内容を渡しています。
        // APIの中でどう処理されるかはAPIにお任せします。（多分何かしらの形で保存されるのでしょう）
        // どうやら "/tweet" はつぶやいた内容を返すようなので、受け取っておきます。
        Tweet tweet = new RestTemplate().postForObject(apiUrl + "/tweet", map, Tweet.class);

        // せっかく受け取ったので、つぶやいた内容をログに出力しておきます。
        logger.info("tweet success!!: {}", tweet);

        // "/sample" にリダイレクトします。
        // マッピングされている #timeline() が動いて、タイムラインが再表示されます。
        return "redirect:sample";
    }

    /**
     * アイコン画像を返す子です。imgタグから呼ばれます。
     *
     * <code>@RequestMapping</code>のvalue属性が指定されているので、クラスに設定されている値に続けられます。
     * つまり、 "/sample/icon/{userId}" と指定しているようなものです。
     * value属性のカッコで括った部分は引数の<code>@PathVariable</code>で受け取れます。
     *
     * また、画像ファイルはテンプレートを使用せずに直接レスポンスに書き出します。
     * この際は<code>@ResponseBody</code>を付与しています。
     *
     * @param userId 欲しいアイコンのユーザーID
     * @return 画像データ
     */
    @RequestMapping("/icon/{userId}")
    @ResponseBody
    public byte[] icon(@PathVariable("userId") String userId) {
        return new RestTemplate().getForObject(apiUrl + "/icon/" + userId, byte[].class);
    }
}
