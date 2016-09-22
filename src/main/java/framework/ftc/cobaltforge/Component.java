package framework.ftc.cobaltforge;

/**
 * Created by Dummyc0m on 9/22/16.
 */
@SuppressWarnings("WeakerAccess")
public enum Component {
    LEFT_STICK_X(Float.TYPE),
    LEFT_STICK_Y(Float.TYPE),
    RIGHT_STICK_X(Float.TYPE),
    RIGHT_STICK_Y(Float.TYPE),
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
    LEFT_TRIGGER(Float.TYPE),
    RIGHT_TRIGGER(Float.TYPE);

    private Class valueType;

    Component(Class valueType) {
        this.valueType = valueType;
    }

    Component() {
        valueType = Boolean.TYPE;
    }

    /**
     * Internal
     *
     * @return value type
     */
    final Class getValueType() {
        return valueType;
    }
}
