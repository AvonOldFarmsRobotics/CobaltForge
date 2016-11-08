package framework.ftc.cobaltforge.kobaltforge.util

import android.os.Environment
import com.qualcomm.robotcore.hardware.HardwareDevice
import com.qualcomm.robotcore.util.RobotLog
import framework.ftc.cobaltforge.kobaltforge.KobaltForge
import framework.ftc.cobaltforge.kobaltforge.annotation.Device
import framework.ftc.cobaltforge.kobaltforge.annotation.Inject
import framework.ftc.cobaltforge.kobaltforge.annotation.State
import framework.ftc.cobaltforge.kobaltforge.exception.IncompatibleInjectionException
import org.json.JSONObject
import org.json.JSONTokener
import java.io.File
import java.io.FileWriter
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField

/**
 * Helper class for Kobalt
 * Created by Dummyc0m on 9/28/16.
 */
internal class Injector(val kobaltForge: KobaltForge) {
    private val kobaltForgeClass = kobaltForge.javaClass.canonicalName
    private val parentFolder = File(Environment.getExternalStorageDirectory(), "config")
    private val configFile = File(parentFolder, kobaltForgeClass + ".json")
    private val config: JSONObject
    private val defaultConfig = JSONObject()

    init {
        if (!configFile.exists()) {
            config = JSONObject()
        } else {
            config = JSONObject(JSONTokener(configFile.bufferedReader().readText()))
        }
    }

    private fun injectState(name: String, property: KMutableProperty1<Any, Any>) {
        var localName = name
        try {
            if ("" == localName) {
                localName = property.javaField?.name ?: "Error"
            }
            RobotLog.a("Attempting to inject $property $name $localName")
            if (config.has(localName)) {
                RobotLog.a("Injecting ${localName} ${config.get(localName)}")
                val type = config.getJSONObject(localName).getString("type")
                val wronglyTyped = config.getJSONObject(localName).getString("value")
                if (wronglyTyped !== null) {
                    val value: Any
                    when (type) {
                        "byte", "java.lang.Byte" -> value = wronglyTyped.toByte()
                        "short", "java.lang.Short" -> value = wronglyTyped.toShort()
                        "int", "java.lang.Integer" -> value = wronglyTyped.toInt()
                        "long", "java.lang.Long" -> value = wronglyTyped.toLong()
                        "float", "java.lang.Float" -> value = wronglyTyped.toFloat()
                        "double", "java.lang.Double" -> value = wronglyTyped.toDouble()
                        "boolean", "java.lang.Boolean" -> value = wronglyTyped.toBoolean()
                        else -> {
                            value = wronglyTyped
                        }
                    }
                    property.set(kobaltForge, value)
                } else {
                    throw NullPointerException("Json state file structure is broken")
                }
            } else {
                val field = property.javaField
                if (field !== null) {
                    RobotLog.a("Writing new Value ${field.get(kobaltForge)}")
                    defaultConfig.put(localName, JSONObject()
                            .put("value", field.get(kobaltForge))
                            .put("type", field.type.canonicalName))
                }
            }
        } catch (e: IllegalArgumentException) {
            throw IncompatibleInjectionException(e)
        } catch (e: ClassCastException) {
            throw IncompatibleInjectionException(property, HardwareDevice::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

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

    private fun injectObject(obj: Any) {
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

    fun saveDefaultConfig() {
        defaultConfig.keys().forEach { e ->
            if (e is String && defaultConfig.has(e)) {
                config.put(e, defaultConfig.get(e))
            }
        }
        if (defaultConfig.length() > 0) {
            if (!parentFolder.exists()) {
                parentFolder.mkdir()
            }
            RobotLog.a("Writing Config $config")
            val writer = FileWriter(configFile)
            writer.write(config.toString())
            writer.flush()
            writer.close()
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
                    injectState(annotation.value, field)
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
}
