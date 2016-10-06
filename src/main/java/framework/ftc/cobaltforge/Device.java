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
}
