package framework.ftc.cobaltforge.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import framework.ftc.cobaltforge.CobaltForge;

/**
 * use @TeleOp to register OpMode
 * Example Set
 * Created by Dummyc0m on 4/12/16.
 */
@Disabled
public class TeleOpControl extends CobaltForge {
    public void onInit() {
        setName("Example");
        addDirective(new SingleMotorDirective());
        addDirective(new SingleMotorDirective());
        addDirective(new SingleMotorDirective()); //Example, don't actually do this
    }
}
