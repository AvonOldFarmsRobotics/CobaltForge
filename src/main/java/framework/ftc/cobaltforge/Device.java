package framework.ftc.cobaltforge;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Injects the HardwareDevice
 * Created by Dummyc0m on 9/22/16.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Device {
    String value() default "";

//    /**
//     * Created by Dummyc0m on 9/22/16.
//     */
//    @Deprecated
//    @Retention(RetentionPolicy.RUNTIME)
//    @interface DcMotor {
//        String value();
//    }
//
//    /**
//     * Created by Dummyc0m on 9/22/16.
//     */
//    @Deprecated
//    @Retention(RetentionPolicy.RUNTIME)
//    @interface Sensor {
//        SensorType type();
//
//        String value();
//    }
//
//    /**
//     * Created by Dummyc0m on 9/22/16.
//     */
//    @Deprecated
//    @Retention(RetentionPolicy.RUNTIME)
//    @interface Servo {
//        String value();
//    }
}
