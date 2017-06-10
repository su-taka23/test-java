package gradle.controller;

import gradle.service.StudyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by suzuki on 2017/04/05.
 */
@Controller
@RequestMapping("/study")
//@RestController
//@EnableAutoConfiguration
public class StudyController {
    private static Logger _log = LoggerFactory.getLogger(StudyController.class);

    @Autowired
    private StudyService studyService;
    private int i ;

    @RequestMapping("/async/{jobs}")
    @ResponseBody
    public String async (@PathVariable("jobs") int jobs) throws Exception {

        _log.info(">>> [start] handleRequest !!");
        final long stat = System.currentTimeMillis();

        if (jobs <= 0)
            return "done.";

        final CountDownLatch latch = new CountDownLatch(jobs);
        List<Future<String>> results = new ArrayList<Future<String>>(jobs);
        for (int i=0; i<jobs; i++) {
            Future<String> res = studyService.process (latch, i * 100L);
            results.add(res);
        }

        latch.await(1000, TimeUnit.MILLISECONDS);

        _log.info(">>> [end] Complete jobs !!: elapsed: {}ms", System.currentTimeMillis() - stat);


//        return "async";
        StringBuilder sb = new StringBuilder();
        for (Future<String> f : results) {
            sb.append("[");
            if (f.isDone()) {
                sb.append(f.get());
            }
            else {
                sb.append("timeout...");
            }
            sb.append("],");
        }

        return sb.toString();
    }

    @ResponseBody
    public String index(){
        _log.info ("!!!! start !!!!");
        final long stat = System.currentTimeMillis();

        i++;

        _log.info (i + " " + stat);

        return "no sync";
    }

}
