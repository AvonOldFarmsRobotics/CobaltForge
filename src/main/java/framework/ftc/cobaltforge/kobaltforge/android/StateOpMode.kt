package framework.ftc.cobaltforge.kobaltforge.android

import android.content.Intent
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import framework.ftc.cobaltforge.kobaltforge.KobaltForge

/**
 * OpMode for the thingy
 * Created by Dummyc0m on 10/21/16.
 */
@TeleOp(name = "Configurator")
class StateOpMode : KobaltForge() {
    override fun construct() {
        onInit {
            val intent = Intent(hardwareMap.appContext, StateActivity::class.java)
            this.hardwareMap.appContext.startActivity(intent)
        }
    }
}