package framework.ftc.cobaltforge.examples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import framework.ftc.cobaltforge.CobaltForge;

/**
 * use @TeleOp to register OpMode
 * Example Set
 * Created by Dummyc0m on 4/12/16.
 */
@Disabled
public class ExampleOpMode extends CobaltForge {
    public void onInit() {
        setName("Example");
        addDirective(new ExampleDirective());
        addDirective(new ExampleDirective());
        addDirective(new ExampleDirective()); //Example, don't actually do this
    }
}
