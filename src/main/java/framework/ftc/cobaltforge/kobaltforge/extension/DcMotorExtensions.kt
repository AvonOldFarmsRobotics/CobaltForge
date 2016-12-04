package framework.ftc.cobaltforge.kobaltforge.extension

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple

/**
 * helper extension functions for DcMotor
 * Created by Dummyc0m on 12/3/16.
 */
fun DcMotor.forward() {
    direction = DcMotorSimple.Direction.FORWARD
}

fun DcMotor.reverse() {
    direction = DcMotorSimple.Direction.REVERSE
}

fun DcMotor.run() {
    power = 1.0
}

fun DcMotor.runReverse() {
    power = -1.0
}

fun DcMotor.runTo(pos: Int) {
    this.targetPosition = pos
}

fun DcMotor.stop(brake: Boolean = true) {
    zeroPowerBehavior = if (brake) DcMotor.ZeroPowerBehavior.BRAKE else DcMotor.ZeroPowerBehavior.FLOAT
    power = 0.0
}
