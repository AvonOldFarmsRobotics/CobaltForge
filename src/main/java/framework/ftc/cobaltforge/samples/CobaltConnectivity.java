package framework.ftc.cobaltforge.samples;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import framework.ftc.cobaltforge.CobaltForge;

/**
 * Created by Dummyc0m on 9/28/16.
 */
@TeleOp(name = "CobaltConnectivity")
public class CobaltConnectivity extends CobaltForge {
    public void onInit() {
        setName("CobaltConnectivity");
        addDirective(new HelloWorldDirective());
    }
}
