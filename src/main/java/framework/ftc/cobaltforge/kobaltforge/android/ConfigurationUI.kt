package framework.ftc.cobaltforge.kobaltforge.android

import org.jetbrains.anko.*

/**
 * UI for Configuration Activity
 * Created by Dummyc0m on 10/21/16.
 */
class ConfigurationUI : AnkoComponent<ConfigurationActivity> {
    override fun createView(ui: AnkoContext<ConfigurationActivity>) = with(ui) {
        listView(theme = 0) {
            padding = dip(30)
            editText {
                hint = "Value1"
                textSize = 24f
            }
            editText {
                hint = "Value2"
                textSize = 24f
            }
            button("Set") {
                textSize = 26f
                onClick {
                    owner.stuff()
                }
            }
        }
    }
}