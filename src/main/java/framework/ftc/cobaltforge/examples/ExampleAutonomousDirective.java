package framework.ftc.cobaltforge.examples;

import com.qualcomm.robotcore.hardware.DcMotor;
import framework.ftc.cobaltforge.AbstractDirective;
import framework.ftc.cobaltforge.Inject;

/**
 * Example Set
 * Created by Dummyc0m on 4/12/16.
 */
@SuppressWarnings("WeakerAccess")
public class ExampleAutonomousDirective extends AbstractDirective {
    @Inject.DcMotor("autoDemo1")
    DcMotor motor1;
    @Inject.DcMotor("autoDemo2")
    DcMotor motor2;

    int target1, target2;

    public void onStart() {
        motor1.setDirection(DcMotor.Direction.FORWARD);
        motor2.setDirection(DcMotor.Direction.REVERSE);
        target1 = motor1.getCurrentPosition() + 1500;
        target2 = motor2.getCurrentPosition() + 1500;
        motor1.setTargetPosition(target1);
        motor2.setTargetPosition(target2);
    }

    public void onLoop() {
        boolean comp = true;
        if (motor1.getCurrentPosition() < target1) {
            comp = false;
        }
        if (motor2.getCurrentPosition() < target2) {
            comp = false;
        }
        if (comp) {
            complete();
        }
    }
}
