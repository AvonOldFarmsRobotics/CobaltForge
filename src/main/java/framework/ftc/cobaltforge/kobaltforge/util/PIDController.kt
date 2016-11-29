package framework.ftc.cobaltforge.kobaltforge.util

/**
 * Created by Dummyc0m on 11/12/16.
 */
class PIDController(val kP: Double, val kI: Double, val kD: Double) {
    var integral = 0.0
    var lastError = 0.0
    var derivative = 0.0

    fun calculate(error: Double): Double {
        derivative = error - lastError
        integral += error
        return kP * error + kI * integral + kD * derivative
    }
}