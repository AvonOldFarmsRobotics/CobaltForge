package framework.ftc.cobaltforge.kobaltforge.util

import com.qualcomm.robotcore.hardware.HardwareDevice
import framework.ftc.cobaltforge.kobaltforge.KobaltForge
import framework.ftc.cobaltforge.kobaltforge.annotation.Device
import framework.ftc.cobaltforge.kobaltforge.annotation.Inject
import framework.ftc.cobaltforge.kobaltforge.exception.IncompatibleInjectionException
import java.lang.reflect.Field

/**
 * Helper class for Kobalt
 * Created by Dummyc0m on 9/28/16.
 */
internal class Injector(val kobaltForge: KobaltForge) {
//    private val injectableMap = HashMap<Class<*>, Any>()

//    @Throws(IllegalAccessException::class, InstantiationException::class)
//    private fun getInjectable(clazz: Class<*>): Any {
//        with(injectableMap[clazz]) {
//            if (this !== null) {
//                return this
//            }
//            with(clazz.newInstance()) {
//                injectableMap.put(clazz, this)
//                return this
//            }
//        }
//    }

    private fun injectObject(obj: Any) {
        val fields = obj.javaClass.declaredFields
        for (field in fields) {
            field.isAccessible = true
            val annotations = field.annotations
            for (annotation in annotations) {
                //val clazz = annotation.annotationClass
                injectField(field, annotation, obj)
            }
        }
    }

    @SuppressWarnings("unchecked")
    fun injectField(field: Field, annotation: Annotation, parent: Any) {
        try {
            when (annotation) {
                is Device -> {
                    injectDevice(annotation.value, field)
                }
                is Inject -> {
                    //val clazz = field.type
                    val obj = field.get(parent)
                    injectObject(obj)
                    println("Injected " + obj)
                    //field.set(parent, obj)
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

    private fun injectDevice(name: String, field: Field) {
        var localName = name
        try {
            if ("" == localName) {
                localName = field.name
            }
            val device = kobaltForge.hardwareMap.get(localName)
            field.set(this, device)
        } catch (e: IllegalArgumentException) {
            throw IncompatibleInjectionException(e)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: ClassCastException) {
            throw IncompatibleInjectionException(field, HardwareDevice::class.java)
        }
    }
}
