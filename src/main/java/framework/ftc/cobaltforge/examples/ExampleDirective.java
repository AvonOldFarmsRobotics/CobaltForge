package framework.ftc.cobaltforge.examples;

import com.qualcomm.robotcore.hardware.DcMotor;
import framework.ftc.cobaltforge.AbstractDirective;
import framework.ftc.cobaltforge.Component;
import framework.ftc.cobaltforge.GamePad1;
import framework.ftc.cobaltforge.Inject;


/**
 * Example Set
 * Created by Dummyc0m on 4/12/16.
 */
@SuppressWarnings("WeakerAccess")
public class ExampleDirective extends AbstractDirective {
    @GamePad1(Component.A)
    boolean a;
    @Inject("demo")
    DcMotor motor;

    public void onStart() {
        motor.setDirection(DcMotor.Direction.FORWARD);
    }

    public void onLoop() {
        if (a) {
            motor.setPower(1.0d);
        }
    }
}
