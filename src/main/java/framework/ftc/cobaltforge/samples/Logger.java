package framework.ftc.cobaltforge.samples;

import com.google.common.collect.EvictingQueue;
import framework.ftc.cobaltforge.CobaltForge;
import framework.ftc.cobaltforge.Inject;

/**
 * Created by Dummyc0m on 9/28/16.
 */
public class Logger {
    private EvictingQueue<Object> logging = EvictingQueue.create(20);

    @Inject
    private CobaltForge cobaltForge;

    public void setSize(int size) {
        logging = EvictingQueue.create(size);
    }

    public Logger append(Object object) {
        logging.offer(object);
        return this;
    }

    public void refresh() {
        for (Object log : logging) {
            cobaltForge.telemetry.addData("Logging", log);
        }
    }
}
