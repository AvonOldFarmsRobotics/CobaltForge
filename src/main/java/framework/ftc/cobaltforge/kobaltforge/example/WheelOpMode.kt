package framework.ftc.cobaltforge.kobaltforge.example

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.util.RobotLog
import framework.ftc.cobaltforge.kobaltforge.KobaltForge
import framework.ftc.cobaltforge.kobaltforge.annotation.Component
import framework.ftc.cobaltforge.kobaltforge.annotation.Device
import framework.ftc.cobaltforge.kobaltforge.annotation.GamePad1
import framework.ftc.cobaltforge.kobaltforge.annotation.Inject
import framework.ftc.cobaltforge.kobaltforge.util.Accumulator

/**
 * An example for KobaltForge
 * Created by Dummyc0m on 10/6/16.
 */
@TeleOp(name = "WheelOpMode")
class WheelOpMode : KobaltForge() {
    @Device
    lateinit var leftMotor: DcMotor

    @Device
    lateinit var rightMotor: DcMotor

    @GamePad1(Component.LEFT_STICK_Y)
    var y = 0F

    @GamePad1(Component.X)
    var x = false

    val leftAccumulator = Accumulator<Double>(0.0) { new, old -> new + old }

    val rightAccumulator = Accumulator<Double>(0.0) { new, old -> new + old }

    @Inject
    val wheelProgram = WheelProgram(leftAccumulator, rightAccumulator)

    override fun construct() {
        name = "CFWheelDrive"

        onInit {
            leftMotor.direction = DcMotorSimple.Direction.FORWARD
            rightMotor.direction = DcMotorSimple.Direction.REVERSE
        }

        onLoop {
            telemetry("X: $x")
            telemetry("Y: $y")
            if (x) {
                true
            } else {
                leftAccumulator.accumulate(-y.toDouble())
                rightAccumulator.accumulate(-y.toDouble())
                wheelProgram.run()
                leftAccumulator.consume { value -> leftMotor.power = value }
                rightAccumulator.consume { value -> rightMotor.power = value }
                false
            }
        }.then {
            telemetry("X: $x")
            telemetry("Y: $y")
            leftMotor.power = -y.toDouble()
            rightMotor.power = -y.toDouble()
            false
        }

        onStop {
            RobotLog.a("We Hate You!")
        }
    }
}