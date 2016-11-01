package framework.ftc.cobaltforge.kobaltforge.android

import android.app.Activity
import android.os.Bundle
import android.preference.PreferenceManager
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.toast

/**
 * Configuration Activity Logic
 * Created by Dummyc0m on 10/21/16.
 */
class StateActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StateUI().setContentView(this)
    }

    fun loadConfiguration(): List<Map<String, Any>> {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        return arrayListOf()
    }

    fun stuff() {
        this.toast("Clicked")
    }
}
