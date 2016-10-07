package framework.ftc.cobaltforge.kobaltforge.example

import framework.ftc.cobaltforge.kobaltforge.annotation.Component
import framework.ftc.cobaltforge.kobaltforge.annotation.GamePad1
import framework.ftc.cobaltforge.kobaltforge.util.Accumulator

/**
 * Inject into custom objects
 * Created by Dummyc0m on 10/6/16.
 */
class WheelProgram(val leftAccumulator: Accumulator<Double>, val rightAccumulator: Accumulator<Double>) {
    @GamePad1(Component.RIGHT_STICK_Y)
    var y = 0F

    fun run() {
        leftAccumulator.accumulate(y.toDouble())
        rightAccumulator.accumulate(y.toDouble())
    }
}