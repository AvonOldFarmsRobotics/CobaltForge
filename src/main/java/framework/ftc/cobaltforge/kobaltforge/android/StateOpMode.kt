package framework.ftc.cobaltforge.kobaltforge.android

import android.content.Intent
import android.os.Bundle
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import framework.ftc.cobaltforge.kobaltforge.KobaltForge

/**
 * OpMode for the thingy
 * Created by Dummyc0m on 10/21/16.
 */
//@TeleOp(name = "Configurator")
@Disabled
open class StateOpMode(val configurableKobaltForge: Class<out KobaltForge>) : KobaltForge() {
    final override fun construct() {
        onInit {
            val intent = Intent(hardwareMap.appContext, StateActivity::class.java)
            val b = Bundle()
            b.putString("configFile", configurableKobaltForge.canonicalName)
            intent.putExtras(b) //Put your id to your next Intent
            this.hardwareMap.appContext.startActivity(intent)
        }
    }
}