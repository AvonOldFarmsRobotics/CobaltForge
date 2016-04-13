package framework.ftc.cobaltforge;

/**
 * Directive to extend
 * Created by Dummyc0m on 4/12/16.
 */
public abstract class AbstractDirective {
    private CobaltForge forge;
    private boolean complete;
    //    private boolean changed;
    private AbstractDirective next;

    /**
     * Called when the opMode inits
     */
    public void onInit() {
    }

    /**
     * Called when the directive execution starts
     * Make sure the set the correct directions and readings etc.
     */
    public abstract void onStart();

    /**
     * Called every loop
     */
    public abstract void onLoop();

    /**
     * Called when the directive execution stops
     */
    @SuppressWarnings("WeakerAccess")
    public void onStop() {
    }

    public final boolean isComplete() {
        return complete;
    }

    protected final void telemetry(String data) {
        forge.telemetry.addData(forge.getName(), data);
    }

    protected final void telemetry(Object data) {
        forge.telemetry.addData(forge.getName(), data);
    }

    protected final void telemetry(float data) {
        forge.telemetry.addData(forge.getName(), data);
    }

    protected final void telemetry(double data) {
        forge.telemetry.addData(forge.getName(), data);
    }

    /**
     * Call when the work is completed for this directive.
     */
    protected final void complete() {
        complete = true;
    }

    final void init(CobaltForge forge) {
        this.forge = forge;
    }

    final ExecutionResult loop() {
        if (complete) {
            if (next == null) {
                return ExecutionResult.CHAIN_COMPLETE;
            }
            return ExecutionResult.RUN_NEXT;
        }
        onLoop();
        return ExecutionResult.RUNNING;
    }

    final void stop() {
        onStop();
        forge = null;
        next = null;
    }

    final AbstractDirective getNext() {
        return next;
    }

    final void setNext(AbstractDirective directive) {
        next = directive;
    }

//    final void setChanged() {
//        changed = true;
//    }
//
//    final boolean getChanged() {
//        if(changed) {
//            changed = false;
//            return true;
//        }
//        return false;
//    }
}
