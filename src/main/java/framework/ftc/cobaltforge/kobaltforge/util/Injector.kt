package framework.ftc.cobaltforge.kobaltforge.util

import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareDevice
import com.qualcomm.robotcore.util.RobotLog
import framework.ftc.cobaltforge.kobaltforge.KobaltForge
import framework.ftc.cobaltforge.kobaltforge.annotation.*
import framework.ftc.cobaltforge.kobaltforge.exception.IncompatibleInjectionException
import java.util.*
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

/**
 * Helper class for Kobalt
 * Created by Dummyc0m on 9/28/16.
 */
internal class Injector(val kobaltForge: KobaltForge) {
    val configManager = ConfigManager(kobaltForge)
    val loopTasks: MutableList<() -> Unit> = ArrayList()

    /**
     * Tosses the device onto the field
     */
    private fun injectDevice(name: String, field: KMutableProperty1<Any, Any>) {
        var localName = name
        try {
            if ("" == localName) {
                localName = field.name
            }
            val device = kobaltForge.hardwareMap.get(localName)
            field.set(kobaltForge, device)
        } catch (e: IllegalArgumentException) {
            throw IncompatibleInjectionException(e)
        } catch (e: ClassCastException) {
            throw IncompatibleInjectionException(field, HardwareDevice::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * tosses the gamepad value onto the field
     */
    private fun injectGamePad(f: KMutableProperty1<Any, Any>, gpc: Component, gamepad: Gamepad, obj: Any) {
        when (gpc) {
            Component.LEFT_STICK_X -> f.set(obj, gamepad.left_stick_x)
            Component.LEFT_STICK_Y -> f.set(obj, gamepad.left_stick_y)
            Component.RIGHT_STICK_X -> f.set(obj, gamepad.right_stick_x)
            Component.RIGHT_STICK_Y -> f.set(obj, gamepad.right_stick_y)
            Component.DPAD_UP -> f.set(obj, gamepad.dpad_up)
            Component.DPAD_DOWN -> f.set(obj, gamepad.dpad_down)
            Component.DPAD_LEFT -> f.set(obj, gamepad.dpad_left)
            Component.DPAD_RIGHT -> f.set(obj, gamepad.dpad_right)
            Component.A -> f.set(obj, gamepad.a)
            Component.B -> f.set(obj, gamepad.b)
            Component.X -> f.set(obj, gamepad.x)
            Component.Y -> f.set(obj, gamepad.y)
            Component.GUIDE -> f.set(obj, gamepad.guide)
            Component.START -> f.set(obj, gamepad.start)
            Component.BACK -> f.set(obj, gamepad.back)
            Component.LEFT_BUMPER -> f.set(obj, gamepad.left_bumper)
            Component.RIGHT_BUMPER -> f.set(obj, gamepad.right_bumper)
            Component.LEFT_STICK_BUTTON -> f.set(obj, gamepad.left_stick_button)
            Component.RIGHT_STICK_BUTTON -> f.set(obj, gamepad.right_stick_button)
            Component.LEFT_TRIGGER -> f.set(obj, gamepad.left_trigger)
            Component.RIGHT_TRIGGER -> f.set(obj, gamepad.right_trigger)
        }
    }

    /**
     * Injects objects (recursively), especially the OpMode instance itself
     */
    fun injectObject(obj: Any) {
        val fields = obj.javaClass.kotlin.declaredMemberProperties
        for (field in fields) {
            field.isAccessible = true
            val annotations = field.annotations
            for (annotation in annotations) {
                //val clazz = annotation.annotationClass
                injectField(field as KMutableProperty1<Any, Any>, annotation, obj)
            }
        }
    }

    @SuppressWarnings("unchecked")
    fun injectField(field: KMutableProperty1<Any, Any>, annotation: Annotation, parent: Any) {
        try {
            RobotLog.a("Attempting to inject $field $annotation")
            when (annotation) {
                is Device -> {
                    injectDevice(annotation.value, field)
                }
                is State -> {
                    configManager.injectState(annotation.value, field)
                }
                is Inject -> {
                    val obj = field.get(parent)
                    injectObject(obj)
                    println("Injected " + obj)
                }
                is GamePad1 -> {
                    loopTasks.add {
                        injectGamePad(field, annotation.value, kobaltForge.gamepad1, parent)
                    }
                }
                is GamePad2 -> {
                    loopTasks.add {
                        injectGamePad(field, annotation.value, kobaltForge.gamepad2, parent)
                    }
                }
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            throw IncompatibleInjectionException(e)
        } catch (e: ClassCastException) {
            throw IncompatibleInjectionException(field, Any::class.java)
        }
    }

    fun saveConfig() {
        configManager.saveDirtyConfig()
    }

    internal fun executeLoopTasks() {
        loopTasks.forEach { it.invoke() }
    }
}
