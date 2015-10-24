package sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author irof
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Bean
    public HandlerExceptionResolver handler() {
        return (request, response, handler, ex) -> {
            logger.error("なんかよくわからないけどとにかく例外が発生したー", ex);
            ModelAndView mav = new ModelAndView();
            mav.addObject("exception", ex);
            mav.setViewName("error");
            return mav;
        };
    }
}
