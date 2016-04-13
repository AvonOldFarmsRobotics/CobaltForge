package framework.ftc.cobaltforge.examples;

import framework.ftc.cobaltforge.CobaltForge;

/**
 * Example Set
 * Created by Dummyc0m on 4/12/16.
 */
public class ExampleOpMode extends CobaltForge {
    public void onInit() {
        setName("Example");
        addDirective(new ExampleDirective());
        addDirective(new ExampleDirective());
        addDirective(new ExampleDirective());
    }
}
