package framework.ftc.cobaltforge.kobaltforge.android

import android.app.Activity
import android.os.Bundle
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.toast

/**
 * Configuration Activity Logic
 * Created by Dummyc0m on 10/21/16.
 */
class ConfigurationActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ConfigurationUI().setContentView(this)
    }

    fun stuff() {
        this.toast("Clicked")
    }
}
