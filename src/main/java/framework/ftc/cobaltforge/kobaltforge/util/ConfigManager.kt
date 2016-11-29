package framework.ftc.cobaltforge.kobaltforge.util

import android.os.Environment
import com.qualcomm.robotcore.hardware.HardwareDevice
import com.qualcomm.robotcore.util.RobotLog
import framework.ftc.cobaltforge.kobaltforge.KobaltForge
import framework.ftc.cobaltforge.kobaltforge.exception.IncompatibleInjectionException
import org.json.JSONObject
import org.json.JSONTokener
import java.io.File
import java.io.FileWriter
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.jvm.javaField

/**
 * Created by Dummyc0m on 11/10/16.
 */
internal class ConfigManager(val kobaltForge: KobaltForge) {
    private val kobaltForgeClass = kobaltForge.javaClass.canonicalName
    private val parentFolder = File(Environment.getExternalStorageDirectory(), "config")
    private val configFile = File(parentFolder, kobaltForgeClass + ".json")
    private val config: JSONObject
    private val dirtyConfig = JSONObject()

    init {
        if (!configFile.exists()) {
            config = JSONObject()
        } else {
            config = JSONObject(JSONTokener(configFile.bufferedReader().readText()))
        }
    }

    fun injectState(name: String, property: KMutableProperty1<Any, Any>) {
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
                    val value = convertType(wronglyTyped, type)
                    property.set(kobaltForge, value)
                } else {
                    throw NullPointerException("Json state file structure is broken")
                }
            } else {
                val field = property.javaField
                if (field !== null) {
                    RobotLog.a("Writing new Value ${field.get(kobaltForge)}")
//                    dirtyConfig.put(localName, JSONObject()
//                            .put("value", field.get(kobaltForge))
//                            .put("type", field.type.canonicalName))
                    put(localName, field.get(kobaltForge))
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

    fun put(name: String, value: Any) {
        RobotLog.a("Saving $value")
        dirtyConfig.put(name, JSONObject()
                .put("value", value)
                .put("type", value.javaClass.canonicalName))
    }

    fun get(name: String, defaultValue: Any): Any {
        if (config.has(name)) {
            val type = config.getJSONObject(name).getString("type")
            val wronglyTyped = config.getJSONObject(name).getString("value")
            if (wronglyTyped !== null) {
                return convertType(wronglyTyped, type)
            } else {
                throw NullPointerException("Json state file structure is broken")
            }
        } else if (dirtyConfig.has(name)) {
            val type = dirtyConfig.getJSONObject(name).getString("type")
            val wronglyTyped = dirtyConfig.getJSONObject(name).getString("value")
            if (wronglyTyped !== null) {
                return convertType(wronglyTyped, type)
            } else {
                throw NullPointerException("Dirty config structure is broken")
            }
        } else {
            put(name, defaultValue)
            return defaultValue
        }
    }

    fun saveDirtyConfig() {
        dirtyConfig.keys().forEach { e ->
            if (e is String && dirtyConfig.has(e)) {
                config.put(e, dirtyConfig.get(e))
            }
        }
        if (dirtyConfig.length() > 0) {
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

    private fun convertType(wronglyTyped: String, type: String): Any {
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
        return value
    }
}