package framework.ftc.cobaltforge.kobaltforge.util

import java.util.*

/**
 * A procedural list of functions
 * Created by Dummyc0m on 10/6/16.
 */
class Blocks {
    private val blockList: MutableList<() -> Unit> = ArrayList()

    fun offer(block: () -> Unit) {
        blockList.add(block)
    }

    fun run() {
        blockList.forEach {
            block ->
            block.invoke()
        }
    }
}