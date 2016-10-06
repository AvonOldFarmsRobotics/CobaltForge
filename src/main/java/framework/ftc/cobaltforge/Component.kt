package framework.ftc.cobaltforge

/**
 * Gamepad component representation
 * Created by Dummyc0m on 9/22/16.
 */
@SuppressWarnings("WeakerAccess")
enum class Component {
    LEFT_STICK_X(java.lang.Float.TYPE),
    LEFT_STICK_Y(java.lang.Float.TYPE),
    RIGHT_STICK_X(java.lang.Float.TYPE),
    RIGHT_STICK_Y(java.lang.Float.TYPE),
    DPAD_UP,
    DPAD_DOWN,
    DPAD_LEFT,
    DPAD_RIGHT,
    A,
    B,
    X,
    Y,
    GUIDE,
    START,
    BACK,
    LEFT_BUMPER,
    RIGHT_BUMPER,
    LEFT_STICK_BUTTON,
    RIGHT_STICK_BUTTON,
    LEFT_TRIGGER(java.lang.Float.TYPE),
    RIGHT_TRIGGER(java.lang.Float.TYPE);

    /**
     * Internal

     * @return value type
     */
    internal var valueType: Class<*>? = null
        private set

    private constructor(valueType: Class<*>) {
        this.valueType = valueType
    }

    private constructor() {
        valueType = java.lang.Boolean.TYPE
    }
}
