package framework.ftc.cobaltforge.samples;

import com.qualcomm.robotcore.hardware.DcMotor;
import framework.ftc.cobaltforge.AbstractDirective;
import framework.ftc.cobaltforge.Component;
import framework.ftc.cobaltforge.Device;
import framework.ftc.cobaltforge.GamePad1;


/**
 * Example Set
 * Created by Dummyc0m on 4/12/16.
 */
@SuppressWarnings("WeakerAccess")
public class SingleMotorDirective extends AbstractDirective {
    @GamePad1(Component.LEFT_STICK_Y)
    float y;
    @Device
    DcMotor motor;

    public void onStart() {
        motor.setDirection(DcMotor.Direction.FORWARD);
    }

    public void onLoop() {
        motor.setPower(y);
    }
}
