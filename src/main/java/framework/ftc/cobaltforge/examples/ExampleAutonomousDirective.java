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
    @Inject("autoDemo1")
    DcMotor motor1;
    @Inject("autoDemo2")
    DcMotor motor2;

    int target1, target2;
    int motor1Inc, motor2Inc;

    public ExampleAutonomousDirective(int motor1Inc, int motor2Inc) {
        this.motor1Inc = motor1Inc;
        this.motor2Inc = motor2Inc;
    }

    public void onStart() {
        motor1.setDirection(DcMotor.Direction.FORWARD);
        motor2.setDirection(DcMotor.Direction.REVERSE);
        target1 = motor1.getCurrentPosition() + motor1Inc;
        target2 = motor2.getCurrentPosition() + motor2Inc;
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
