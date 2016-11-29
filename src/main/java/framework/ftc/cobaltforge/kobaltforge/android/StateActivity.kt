package framework.ftc.cobaltforge.kobaltforge.android

import android.app.Activity
import android.os.Bundle
import android.os.Environment
import com.qualcomm.robotcore.util.RobotLog
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.toast
import org.json.JSONObject
import org.json.JSONTokener
import java.io.File
import java.util.*

/**
 * Configuration Activity Logic
 * Created by Dummyc0m on 10/21/16.
 */
class StateActivity() : Activity() {
    var className: String = ""
    val config = HashMap<String, JSONObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.extras !== null) {
            className = intent.extras.getString("configFile")
        }
        try {
            loadConfig()
        } catch (e: Exception) {
            RobotLog.a("Nothing Exists, Run the Configurable OpMode first")
        }
        StateUI().setContentView(this)
    }

    fun loadConfig() {
        val configFile = File(File(Environment.getExternalStorageDirectory(), "config"), className + ".json")
        val jsonConfig = JSONObject(JSONTokener(configFile.readText()))
        jsonConfig.keys().forEach { e ->
            if (e is String) {
                config.put(e, jsonConfig.getJSONObject(e))
            }
        }
    }

    fun saveConfig(): Boolean {
        val jsonConfig = JSONObject(config)
        try {
            RobotLog.a(jsonConfig.toString())
            File(File(Environment.getExternalStorageDirectory(), "config"), className + ".json").writeText(jsonConfig.toString())
            return true
        } catch (e: Exception) {
            RobotLog.logStacktrace(e)
            return false
        }
    }

    fun stuff() {
        this.toast("Clicked")
    }
}
