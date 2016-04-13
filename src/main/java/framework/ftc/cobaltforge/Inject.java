package framework.ftc.cobaltforge;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Example Set
 * Created by Dummyc0m on 4/11/16.
 */
@SuppressWarnings("WeakerAccess")
public final class Inject {
    @SuppressWarnings("WeakerAccess")
    public enum SensorType {
        LEGACY_MODULE,
        TOUCH_SENSOR_MULTIPLEXER,
        DEVICE_INTERFACE_MODULE,
        ANALOG_INPUT,
        DIGITAL_CHANNEL,
        OPTICAL_DISTANCE_SENSOR,
        TOUCH_SENSOR,
        PWM_OUTPUT,
        I2C_DEVICE,
        ANALOG_OUTPUT,
        COLOR_SENSOR,
        LED,
        ACCELERATION_SENSOR,
        COMPASS_SENSOR,
        GYRO_SENSOR,
        IR_SEEKER_SENSOR,
        LIGHT_SENSOR,
        ULTRASONIC_SENSOR,
        VOLTAGE_SENSOR
    }

    @SuppressWarnings("WeakerAccess")
    public enum GamePadComponent {
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

        GamePadComponent(Class valueType) {
            this.valueType = valueType;
        }

        GamePadComponent() {
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

    @Retention(RetentionPolicy.RUNTIME)
    public @interface GamePad1 {
        GamePadComponent value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface GamePad2 {
        GamePadComponent value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface DcMotor {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Servo {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Sensor {
        SensorType type();

        String value();
    }
}
