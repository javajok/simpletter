package javajok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * アプリケーションのエントリポイントとなるクラスです。
 * 動作を確認したい場合は、IDEからこのクラスを実行してください。
 *
 * このクラスの実装はSpringBoot固有であり、書き方はほぼ固定になります。
 * そのため、このクラスは *ワークショップ対象外* です。とりあえずは気にしないでください。
 *
 * @author irof
 */
@Controller
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * http://localhost:8080 にアクセスした際に動作するコントローラーです。
     * このメソッドを動作させるために Application クラスに @Controller を付与していますが、
     * 無くてもSpringBootは動作します。
     */
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
