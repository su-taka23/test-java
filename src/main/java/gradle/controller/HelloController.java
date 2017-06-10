package gradle.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by suzuki on 2017/04/03.
 */
@RestController
@EnableAutoConfiguration
public class HelloController {
    @RequestMapping("/test")
//    @ResponseBody
    public String index() {
        return "Hello world vbbaDbaa";





    }


}
