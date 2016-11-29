package framework.ftc.cobaltforge.kobaltforge.util

import org.firstinspires.ftc.robotcore.external.ClassFactory
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables

/**
 * Created by Dummyc0m on 11/12/16.
 */
class VuforiaLocator(assets: String,
                     cameraMonitorViewIdParent: Int,
                     licenseKey: String,
                     cameraDirection: VuforiaLocalizer.CameraDirection,
        // Robot
                     mmRobotX: Double,
                     mmRobotY: Double,
                     mmFTCFieldWidth: Double) {
    private val lastLocation: OpenGLMatrix? = null

    /**
     * [.vuforia] is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private lateinit var vuforia: VuforiaLocalizer

    private val trackables: VuforiaTrackables

    private val phoneLocation: OpenGLMatrix? = null

    private val mmPerInch = 25.4f
    private val mmBotWidth = 18 * mmPerInch            // ... or whatever is right for your robot
    private val mmFTCFieldWidth = (12 * 12 - 2) * mmPerInch   // the FTC field is ~11'10" center-to-center of the glass panels

    init {
        trackables = this.vuforia.loadTrackablesFromAsset(assets)
        val params = VuforiaLocalizer.Parameters(cameraMonitorViewIdParent)
        params.cameraDirection = cameraDirection
        params.vuforiaLicenseKey = licenseKey
        vuforia = ClassFactory.createVuforiaLocalizer(params)
    }
}