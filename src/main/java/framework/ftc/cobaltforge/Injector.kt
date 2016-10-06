package framework.ftc.cobaltforge

import framework.ftc.cobaltforge.exceptions.IncompatibleInjectionException
import java.lang.reflect.Field
import java.util.*

/**
 * Created by Dummyc0m on 9/28/16.
 */
internal object Injector {
    private val injectableMap = HashMap<Class<*>, Any>()
    var runningCobalt: CobaltForge? = null

    @Throws(IllegalAccessException::class, InstantiationException::class)
    private fun getInjectable(clazz: Class<*>): Any? {
        return if (injectableMap.containsKey(clazz)) injectableMap[clazz] else clazz.newInstance()
    }

    private fun injectObject(obj: Any) {
        val fields = obj.javaClass.declaredFields
        for (field in fields) {
            field.isAccessible = true
            val annotations = field.annotations
            for (annotation in annotations) {
                val clazz = annotation.annotationClass
                if (clazz == Inject::class.java) {
                    injectField(field, obj)
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    fun injectField(field: Field, directive: Any) {
        try {
            val clazz = field.type
            if (clazz == CobaltForge::class.java) {
                println("Injected CobaltForge")
                field.set(directive, runningCobalt)
            } else {
                val obj = getInjectable(clazz)
                injectObject(obj!!)
                println("Injected " + obj)
                field.set(directive, obj)
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            throw IncompatibleInjectionException(e)
        } catch (e: ClassCastException) {
            throw IncompatibleInjectionException(field, Any::class.java)
        }

    }
}
