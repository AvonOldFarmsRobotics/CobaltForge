package framework.ftc.cobaltforge.kobaltforge

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Gamepad
import framework.ftc.cobaltforge.kobaltforge.annotation.Component
import framework.ftc.cobaltforge.kobaltforge.annotation.GamePad1
import framework.ftc.cobaltforge.kobaltforge.annotation.GamePad2
import framework.ftc.cobaltforge.kobaltforge.util.Blocks
import framework.ftc.cobaltforge.kobaltforge.util.Injector
import framework.ftc.cobaltforge.kobaltforge.util.LoopExecutor
import java.util.*
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

/**
 * The brand new Kobalt Forge!
 * Created by Dummyc0m on 4/7/16.
 */
@Disabled
abstract class KobaltForge : OpMode() {
    //    private val gamePad1Mapping: MutableMap<Component, MutableList<Field>> = HashMap()
//    private val gamePad2Mapping: MutableMap<Component, MutableList<Field>> = HashMap()
    private val gamePad1Mapping: MutableMap<Component, MutableList<KMutableProperty1<Any, Any>>> = HashMap()
    private val gamePad2Mapping: MutableMap<Component, MutableList<KMutableProperty1<Any, Any>>> = HashMap()
    private val injector = Injector(this)

    protected var name = "KobaltForge"

    private val loop = Blocks()
    private val loopInit = Blocks()
    private val init = Blocks()
    private val start = Blocks()
    private val stop = Blocks()

    private var notStarted = true

    abstract fun construct()

    fun onLoop(block: () -> Boolean): LoopExecutor {
        with(LoopExecutor(block)) {
            if (notStarted)
                loop.offer(this)
            else
                throw IllegalAccessException("Adding blocks after init")
            return this
        }
    }

    fun onLoopInit(block: () -> Unit) {
        if (notStarted)
            loopInit.offer(block)
        else
            throw IllegalAccessException("Adding blocks after init")
    }

    fun onInit(block: () -> Unit) {
        if (notStarted)
            init.offer(block)
        else
            throw IllegalAccessException("Adding blocks after init")
    }

    fun onStart(block: () -> Unit) {
        if (notStarted)
            start.offer(block)
        else
            throw IllegalAccessException("Adding blocks after init")
    }

    fun onStop(block: () -> Unit) {
        if (notStarted)
            stop.offer(block)
        else
            throw IllegalAccessException("Adding blocks after init")
    }

    final override fun init_loop() {
        loopInit.run()
    }

    final override fun start() {
        start.run()
    }

    final override fun stop() {
        stop.run()
    }

    final override fun init() {
        construct()
        notStarted = false
        RuntimeException(this.javaClass.canonicalName).printStackTrace()
        this.javaClass.kotlin.declaredMemberProperties.forEach { kProperty ->
            if (kProperty is KMutableProperty1<KobaltForge, *>) {
                kProperty.isAccessible = true
                kProperty.annotations.forEach { annotation ->
                    when (annotation) {
                        is GamePad1 -> {
                            var list = gamePad1Mapping[annotation.value]
                            if (list == null) {
                                list = ArrayList()
                                gamePad1Mapping[annotation.value] = list
                            }
                            list.add(kProperty as KMutableProperty1<Any, Any>)
                        }
                        is GamePad2 -> {
                            var list = gamePad2Mapping[annotation.value]
                            if (list == null) {
                                list = ArrayList()
                                gamePad2Mapping[annotation.value] = list
                            }
                            list.add(kProperty as KMutableProperty1<Any, Any>)
                        }
                        else -> {
                            injector.injectField(kProperty as KMutableProperty1<Any, Any>, annotation, this)
                        }
                    }
                }
            }
        }
//        this.javaClass.declaredFields.forEach { field ->
//            field.isAccessible = true
//            RuntimeException(field.name).printStackTrace()
//            field.annotations.forEach { annotation ->
//                RuntimeException(annotation.toString() + " " + (annotation is Device)).printStackTrace()
//                when (annotation) {
//                    is GamePad1 -> {
//                        var list = gamePad1Mapping[annotation.value]
//                        if (list == null) {
//                            list = ArrayList()
//                            gamePad1Mapping[annotation.value] = list
//                        }
//                        list.add(field)
//                    }
//                    is GamePad2 -> {
//                        var list = gamePad2Mapping[annotation.value]
//                        if (list == null) {
//                            list = ArrayList()
//                            gamePad2Mapping[annotation.value] = list
//                        }
//                        list.add(field)
//                    }
//                    else -> {
//                        injector.injectField(field, annotation, this)
//                    }
//                }
//            }
//        }
        init.run()
    }

    final override fun loop() {
        gamePad1Mapping.forEach { entry ->
            entry.value.forEach { field ->
                injectGamePad(field, entry.key, gamepad1)
            }
        }
        gamePad2Mapping.forEach { entry ->
            entry.value.forEach { field ->
                injectGamePad(field, entry.key, gamepad2)
            }
        }
        loop.run()
    }

    private fun injectGamePad(f: KMutableProperty1<Any, Any>, gpc: Component, gamepad: Gamepad) {
        when (gpc) {
            Component.LEFT_STICK_X -> f.set(this, gamepad.left_stick_x)
            Component.LEFT_STICK_Y -> f.set(this, gamepad.left_stick_y)
            Component.RIGHT_STICK_X -> f.set(this, gamepad.right_stick_x)
            Component.RIGHT_STICK_Y -> f.set(this, gamepad.right_stick_y)
            Component.DPAD_UP -> f.set(this, gamepad.dpad_up)
            Component.DPAD_DOWN -> f.set(this, gamepad.dpad_down)
            Component.DPAD_LEFT -> f.set(this, gamepad.dpad_left)
            Component.DPAD_RIGHT -> f.set(this, gamepad.dpad_right)
            Component.A -> f.set(this, gamepad.a)
            Component.B -> f.set(this, gamepad.b)
            Component.X -> f.set(this, gamepad.x)
            Component.Y -> f.set(this, gamepad.y)
            Component.GUIDE -> f.set(this, gamepad.guide)
            Component.START -> f.set(this, gamepad.start)
            Component.BACK -> f.set(this, gamepad.back)
            Component.LEFT_BUMPER -> f.set(this, gamepad.left_bumper)
            Component.RIGHT_BUMPER -> f.set(this, gamepad.right_bumper)
            Component.LEFT_STICK_BUTTON -> f.set(this, gamepad.left_stick_button)
            Component.RIGHT_STICK_BUTTON -> f.set(this, gamepad.right_stick_button)
            Component.LEFT_TRIGGER -> f.set(this, gamepad.left_trigger)
            Component.RIGHT_TRIGGER -> f.set(this, gamepad.right_trigger)
        }
    }

    fun telemetry(anything: Any?) {
        telemetry.addData(name, anything)
    }
}