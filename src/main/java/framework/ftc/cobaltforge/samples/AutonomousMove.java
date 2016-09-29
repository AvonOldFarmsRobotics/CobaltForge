package framework.ftc.cobaltforge.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import framework.ftc.cobaltforge.CobaltForge;

/**
 * use @Autonomous to register OpMode
 * Example Set
 * Created by Dummyc0m on 4/12/16.
 */
@Disabled
public class AutonomousMove extends CobaltForge {
    public void onInit() {
        setName("ExampleAutonomous");
        addDirective(new MoveTankDirective(1500, 1500))
                .then(new MoveTankDirective(-200, -200))
                .then(new MoveTankDirective(0, 100)); //this actually works
    }
}
