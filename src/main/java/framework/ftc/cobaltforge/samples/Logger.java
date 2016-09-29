package framework.ftc.cobaltforge.samples;

import com.google.common.collect.EvictingQueue;
import framework.ftc.cobaltforge.Inject;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Dummyc0m on 9/28/16.
 */
public class Logger {
    private EvictingQueue<Object> logging = EvictingQueue.create(20);

    @Inject
    private Telemetry telemetry;

    void setSize(int size) {
        logging = EvictingQueue.create(size);
    }

    Logger append(Object object) {
        logging.offer(object);
        return this;
    }

    void refresh() {
        for (Object log : logging) {
            telemetry.addData("Logging", log);
        }
    }
}
