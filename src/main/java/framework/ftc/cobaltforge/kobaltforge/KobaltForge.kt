package framework.ftc.cobaltforge.kobaltforge

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import framework.ftc.cobaltforge.kobaltforge.util.Blocks
import framework.ftc.cobaltforge.kobaltforge.util.Injector
import framework.ftc.cobaltforge.kobaltforge.util.LoopExecutor

/**
 * The brand new Kobalt Forge!
 * Created by Dummyc0m on 4/7/16.
 */
@Disabled
abstract class KobaltForge : OpMode() {
    // The magical object powering all the reflection
    private val injector = Injector(this)

    // You may change this in construct... Fine, anywhere.
    protected var name = "KobaltForge"

    // Registered blocks (from construct)
    private val loop = Blocks()
    private val loopInit = Blocks()
    private val init = Blocks()
    private val start = Blocks()
    private val stop = Blocks()

    // Makes sure that nothing gets added after the program starts running. Theoretically you could, but it's a design decision
    private var notStarted = true

    abstract fun construct()

    fun onLoop(block: () -> Boolean): LoopExecutor {
        with(LoopExecutor(block)) {
            if (notStarted)
                loop.offer(this)
            else
                throw IllegalAccessException("Adding blocks after init")
            return this
        }
    }

    fun onLoopInit(block: () -> Unit) {
        if (notStarted)
            loopInit.offer(block)
        else
            throw IllegalAccessException("Adding blocks after init")
    }

    fun onInit(block: () -> Unit) {
        if (notStarted)
            init.offer(block)
        else
            throw IllegalAccessException("Adding blocks after init")
    }

    fun onStart(block: () -> Unit) {
        if (notStarted)
            start.offer(block)
        else
            throw IllegalAccessException("Adding blocks after init")
    }

    fun onStop(block: () -> Unit) {
        if (notStarted)
            stop.offer(block)
        else
            throw IllegalAccessException("Adding blocks after init")
    }

    final override fun init_loop() {
        loopInit.run()
    }

    final override fun start() {
        start.run()
    }

    final override fun stop() {
        stop.run()
    }

    final override fun init() {
        construct()
        notStarted = false
        injector.injectObject(this)
        saveConfig()
        init.run()
    }

    final override fun loop() {
        injector.executeLoopTasks()
        loop.run()
    }

    fun telemetry(anything: Any?) {
        telemetry.addData(name, anything)
    }

    /**
     * Works only when started
     */
    fun saveConfig() {
        if (!notStarted) {
            injector.saveConfig()
        }
    }

    fun put(name: String, value: Any) {
        injector.configManager.put(name, value)
    }

    fun get(name: String, defaultValue: Any): Any {
        return injector.configManager.get(name, defaultValue)
    }
}