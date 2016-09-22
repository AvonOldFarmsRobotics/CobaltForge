package framework.ftc.cobaltforge;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Injects Components on GP1
 * Created by Dummyc0m on 9/22/16.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface GamePad1 {
    Component value();
}
