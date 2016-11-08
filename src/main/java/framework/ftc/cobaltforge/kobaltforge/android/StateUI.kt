package framework.ftc.cobaltforge.kobaltforge.android

import android.animation.LayoutTransition
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.LinearLayout
import org.jetbrains.anko.*

/**
 * UI for Configuration Activity
 * Created by Dummyc0m on 10/21/16.
 */
class StateUI : AnkoComponent<StateActivity> {
    override fun createView(ui: AnkoContext<StateActivity>) = with(ui) {
//        val stateAdapter = StateViewAdapter(owner)
//        verticalLayout {
//            relativeLayout {
//                textView("KobaltForge").lparams {
//                    centerHorizontally()
//                }
//            }
//            scrollView {
//                adapter = stateAdapter
//            }
//            button("Save") {
//                onClick {
//                    if (owner.saveConfig()) {
//                        toast("Success")
//                    } else {
//                        toast("Fail")
//                    }
//                }
//            }
//        }
        scrollView {
            verticalLayout {
                padding = dip(16)
                layoutTransition = LayoutTransition()
                for ((key, value) in owner.config.entries) {
                    verticalLayout {
//                        id = item.id
                        orientation = LinearLayout.HORIZONTAL
                        textView(key) {
//                            weight = 2f
                        }
                        editText {
//                            id = item.inputId
//                                textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                            setText(value.get("value").toString())
                            val type = value.getString("type")
                            when (type) {
                                "byte", "java.lang.Byte" -> inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
                                "short", "java.lang.Short" -> inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
                                "int", "java.lang.Integer" -> inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
                                "long", "java.lang.Long" -> inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
                                "float", "java.lang.Float" -> inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                                "double", "java.lang.Double" -> inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                                "boolean", "java.lang.Boolean" -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                                else -> {
                                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                                }
                            }
                            addTextChangedListener(object : TextWatcher {
                                override fun afterTextChanged(s: Editable?) {
                                }

                                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                                }

                                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                                    RobotLog.a("On Value " + s)
                                    if (s !== null) {
                                        val str = s.toString()
                                        when (type) {
                                            "byte", "java.lang.Byte" -> value.put("value", str.toByte())
                                            "short", "java.lang.Short" -> value.put("value", str.toShort())
                                            "int", "java.lang.Integer" -> value.put("value", str.toInt())
                                            "long", "java.lang.Long" -> value.put("value", str.toLong())
                                            "float", "java.lang.Float" -> value.put("value", str.toFloat())
                                            "double", "java.lang.Double" -> value.put("value", str.toDouble())
                                            "boolean", "java.lang.Boolean" -> value.put("value", str.toBoolean())
                                            else -> {
                                                value.put("value", str)
                                            }
                                        }
                                    }
                                }
                            })
                        }.lparams(width = dip(0)) { weight = 1f }
                    }.lparams(width = matchParent, height = dip(42))
                }
                button("Save") {
                    onClick {
                        if (owner.saveConfig()) {
                            toast("Config Saved")
                        } else {
                            toast("")
                        }
                    }
                }
            }
        }
    }
}
