package framework.ftc.cobaltforge.samples;

import framework.ftc.cobaltforge.AbstractDirective;
import framework.ftc.cobaltforge.Inject;

import java.util.Date;

/**
 * Created by Dummyc0m on 9/28/16.
 */
public class HelloWorldDirective extends AbstractDirective {
    @Inject
    private LoopStatistics tracker;
    @Inject
    private Logger logger;

    public void onStart() {
    }

    public void onLoop() {
        tracker.loops++;
        logger.append("Cobalt is Working... " + new Date().toString())
                .refresh();
    }
}
