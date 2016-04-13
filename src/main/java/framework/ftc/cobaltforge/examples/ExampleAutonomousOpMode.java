package framework.ftc.cobaltforge.examples;

import framework.ftc.cobaltforge.CobaltForge;

/**
 * Example Set
 * Created by Dummyc0m on 4/12/16.
 */
public class ExampleAutonomousOpMode extends CobaltForge {
    public void onInit() {
        setName("ExampleAutonomous");
        addDirective(new ExampleAutonomousDirective())
                .then(new ExampleAutonomousDirective())
                .then(new ExampleAutonomousDirective());
    }
}
