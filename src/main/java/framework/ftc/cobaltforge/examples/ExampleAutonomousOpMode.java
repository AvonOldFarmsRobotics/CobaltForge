package framework.ftc.cobaltforge.examples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import framework.ftc.cobaltforge.CobaltForge;

/**
 * use @Autonomous to register OpMode
 * Example Set
 * Created by Dummyc0m on 4/12/16.
 */
@Disabled
public class ExampleAutonomousOpMode extends CobaltForge {
    public void onInit() {
        setName("ExampleAutonomous");
        addDirective(new ExampleAutonomousDirective(1500, 1500))
                .then(new ExampleAutonomousDirective(-200, -200))
                .then(new ExampleAutonomousDirective(0, 100)); //this actually works
    }
}
