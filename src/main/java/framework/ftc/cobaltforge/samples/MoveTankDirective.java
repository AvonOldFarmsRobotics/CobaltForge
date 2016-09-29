package framework.ftc.cobaltforge.samples;

import com.qualcomm.robotcore.hardware.DcMotor;
import framework.ftc.cobaltforge.AbstractDirective;
import framework.ftc.cobaltforge.Device;
import framework.ftc.cobaltforge.Inject;

/**
 * Example Set
 * Created by Dummyc0m on 4/12/16.
 */
@SuppressWarnings("WeakerAccess")
public class MoveTankDirective extends AbstractDirective {
    @Inject
    LoopStatistics tracker;
    @Device
    DcMotor leftMotor;
    @Device
    DcMotor rightMotor;

    int leftTarget, rightTarget;
    int leftInc, rightInc;

    public MoveTankDirective(int leftInc, int rightInc) {
        this.leftInc = leftInc;
        this.rightInc = rightInc;
    }

    public void onStart() {
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        leftTarget = leftMotor.getCurrentPosition() + leftInc;
        rightTarget = rightMotor.getCurrentPosition() + rightInc;
        leftMotor.setTargetPosition(leftTarget);
        rightMotor.setTargetPosition(rightTarget);
    }

    public void onLoop() {
        if (leftMotor.getCurrentPosition() >= leftTarget && rightMotor.getCurrentPosition() >= rightTarget) {
            complete();
        }
        tracker.loops++;
        telemetry("Looped: " + tracker.loops);
    }
}
