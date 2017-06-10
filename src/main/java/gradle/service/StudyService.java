package gradle.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;


/**
 * Created by suzuki on 2017/04/06.
 */
@Service
public class StudyService {

    private static Logger _log = LoggerFactory
            .getLogger(StudyService.class);



    @Async
    public Future<String> process(final CountDownLatch latch, final Long waitMillis) {

        try {
        _log.info("[start] processWithAsync(wait={}ms)!!", waitMillis);


        try {
            Thread.sleep(waitMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        _log.info("[ end ] processWithAsync(wait={}ms)!!", waitMillis);

        return new AsyncResult<String>(
                new StringBuilder("ID-").append(Thread.currentThread().getName())
                        .append(Thread.currentThread().getId()).toString());

//        return null;

    } finally {
        latch.countDown();
    }
    }
}
