package framework.ftc.cobaltforge.kobaltforge.android

import org.jetbrains.anko.*

/**
 * UI for Configuration Activity
 * Created by Dummyc0m on 10/21/16.
 */
class StateUI : AnkoComponent<StateActivity> {
    override fun createView(ui: AnkoContext<StateActivity>) = with(ui) {
//        mAdapter = MyAdapter(owner)
        verticalLayout {
            relativeLayout {
                textView("Students").lparams {
                    centerHorizontally()
                }
            }
            listView {
//                adapter = mAdapter
            }
        }
    }
}