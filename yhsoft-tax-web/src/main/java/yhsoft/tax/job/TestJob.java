package yhsoft.tax.job;

import org.springframework.stereotype.Component;

/**
 * Created by zhuang on 3/18/2018.
 */
@Component
public class TestJob {

    private int i = 0;

    //@Scheduled(fixedRate = 2000)
    public void execute() {
        System.out.println("fixedRate:" + (i++));
    }

}
