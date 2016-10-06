package framework.ftc.cobaltforge.samples

import com.google.common.collect.EvictingQueue
import framework.ftc.cobaltforge.CobaltForge
import framework.ftc.cobaltforge.Inject

/**
 * Created by Dummyc0m on 9/28/16.
 */
class Logger {
    private var logging = EvictingQueue.create<Any>(20)

    @Inject
    private val cobaltForge: CobaltForge? = null

    fun setSize(size: Int) {
        logging = EvictingQueue.create<Any>(size)
    }

    fun append(`object`: Any): Logger {
        logging.offer(`object`)
        return this
    }

    fun refresh() {
        for (log in logging) {
            cobaltForge!!.telemetry.addData("Logging", log)
        }
    }
}
