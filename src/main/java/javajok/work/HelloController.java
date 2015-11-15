package javajok.work;

import org.springframework.stereotype.Controller;

@Controller
public class HelloController {

    public String hello() {
        return "hello";
    }
}
