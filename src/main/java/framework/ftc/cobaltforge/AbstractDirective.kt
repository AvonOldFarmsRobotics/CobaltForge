package framework.ftc.cobaltforge

/**
 * Directive to extend
 * Created by Dummyc0m on 4/12/16.
 */
abstract class AbstractDirective {
    private var forge: CobaltForge? = null
    var isComplete: Boolean = false
        private set
    //    private boolean changed;
    internal var next: AbstractDirective? = null

    /**
     * Called when the opMode inits
     */
    open fun onInit() {
    }

    /**
     * Called when the directive execution starts
     * Make sure the set the correct directions and readings etc.
     */
    abstract fun onStart()

    /**
     * Called every loop
     */
    abstract fun onLoop()

    /**
     * Called when the directive execution stops
     */
    @SuppressWarnings("WeakerAccess")
    open fun onStop() {
    }

    override fun toString(): String {
        return "AbstractDirective{" +
                "forge=" + forge +
                ", complete=" + isComplete +
                ", next=" + next +
                '}'
    }

    protected fun telemetry(data: String) {
        forge!!.telemetry.addData(forge!!.name, data)
    }

    protected fun telemetry(data: Any) {
        forge!!.telemetry.addData(forge!!.name, data)
    }

    protected fun telemetry(data: Float) {
        forge!!.telemetry.addData(forge!!.name, data)
    }

    protected fun telemetry(data: Double) {
        forge!!.telemetry.addData(forge!!.name, data)
    }

    /**
     * Call when the work is completed for this directive.
     */
    protected fun complete() {
        isComplete = true
    }

    internal fun init(forge: CobaltForge) {
        this.forge = forge
    }

    internal fun loop(): ExecutionResult {
        if (isComplete) {
            if (next == null) {
                return ExecutionResult.CHAIN_COMPLETE
            }
            return ExecutionResult.RUN_NEXT
        }
        onLoop()
        return ExecutionResult.RUNNING
    }

    internal fun stop() {
        onStop()
        forge = null
        next = null
    }
}
