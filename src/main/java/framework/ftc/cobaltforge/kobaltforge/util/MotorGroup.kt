package framework.ftc.cobaltforge.kobaltforge.util

import com.qualcomm.robotcore.hardware.DcMotor

/**
 * Created by Dummyc0m on 2/4/17.
 */
class MotorGroup(vararg val dcMotor: DcMotor) {
    fun setPowers(doubleArray: DoubleArray) {
        if (doubleArray.size !== dcMotor.size) {
            throw IllegalArgumentException("PowerArray size does not match motorgroup size")
        }
        dcMotor.forEachIndexed { i, dcMotor -> dcMotor.power = doubleArray[i] }
    }
}