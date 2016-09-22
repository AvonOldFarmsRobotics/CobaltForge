package framework.ftc.cobaltforge.examples;

import framework.ftc.cobaltforge.CobaltForge;

/**
 * Example Set
 * Created by Dummyc0m on 4/12/16.
 */
public class ExampleAutonomousOpMode extends CobaltForge {
    public void onInit() {
        setName("ExampleAutonomous");
        addDirective(new ExampleAutonomousDirective(1500, 1500))
                .then(new ExampleAutonomousDirective(-200, -200))
                .then(new ExampleAutonomousDirective(0, 100)); //this actually works
    }
}
