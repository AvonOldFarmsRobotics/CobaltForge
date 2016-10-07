package framework.ftc.cobaltforge.kobaltforge.util

import java.util.*

/**
 * Helper tool to execute loops
 * Created by Dummyc0m on 10/6/16.
 */
class LoopExecutor(firstBlock: () -> Boolean) : () -> Unit {
    private val chain = LinkedList<() -> Boolean>()
    private var running: (() -> Boolean)? = null

    init {
        chain.offer(firstBlock)
    }

    override fun invoke() {
        if (running == null && chain.isNotEmpty()) {
            running = chain.poll()
        }
        val result = running?.invoke()
        if (result !== null && result) {
            if (chain.isEmpty()) {
                running = null
            } else {
                running = chain.poll()
            }
        }
    }

    fun then(block: () -> Boolean): LoopExecutor {
        chain.offer(block)
        return this
    }
}