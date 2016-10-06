package framework.ftc.cobaltforge

/**
 * Internal
 * Created by Dummyc0m on 4/12/16.
 */
internal enum class ExecutionResult {
    /**
     * Directive still working
     */
    RUNNING,
    /**
     * Directive chain complete, no next available
     */
    CHAIN_COMPLETE,
    /**
     * Run the next directive in chain
     */
    RUN_NEXT
}
