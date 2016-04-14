package framework.ftc.cobaltforge;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import framework.ftc.cobaltforge.Inject.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CobaltForge OpMode
 * Created by Dummyc0m on 4/7/16.
 */
@SuppressWarnings("WeakerAccess")
public abstract class CobaltForge extends OpMode {
    private Map<GamePadComponent, List<DirectiveFieldPair>> gamePadMap1 = new HashMap<GamePadComponent, List<DirectiveFieldPair>>();
    private Map<GamePadComponent, List<DirectiveFieldPair>> gamePadMap2 = new HashMap<GamePadComponent, List<DirectiveFieldPair>>();

    private List<AbstractDirective> handlerStore = new ArrayList<AbstractDirective>();
    private List<AbstractDirective> handlers = new ArrayList<AbstractDirective>();

    private String name = "CobaltForge";
    private boolean prohibited;

    public abstract void onInit();

    @Override
    public final void init() {
        prohibited = false;
        onInit();
        prohibited = true;
        for (AbstractDirective directive : handlerStore) {
            Field[] fields = directive.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation : annotations) {
                    Class clazz = annotation.annotationType();
                    if (clazz == GamePad1.class) {
                        GamePad1 gamePad1 = ((GamePad1) annotation);
                        ensureType(field, gamePad1.value().getValueType());
                        List<DirectiveFieldPair> fieldList = gamePadMap1.get(gamePad1.value());
                        if (fieldList == null) {
                            fieldList = new ArrayList<DirectiveFieldPair>();
                            gamePadMap1.put(gamePad1.value(), fieldList);
                        }
                        fieldList.add(new DirectiveFieldPair(field, directive));
                    } else if (clazz == GamePad2.class) {
                        GamePad2 gamePad2 = ((GamePad2) annotation);
                        ensureType(field, gamePad2.value().getValueType());
                        List<DirectiveFieldPair> fieldList = gamePadMap2.get(gamePad2.value());
                        if (fieldList == null) {
                            fieldList = new ArrayList<DirectiveFieldPair>();
                            gamePadMap2.put(gamePad2.value(), fieldList);
                        }
                        fieldList.add(new DirectiveFieldPair(field, directive));
                    } else if (clazz == DcMotor.class) {
                        DcMotor dcMotor = ((DcMotor) annotation);
                        ensureType(field, com.qualcomm.robotcore.hardware.DcMotor.class);
                        try {
                            field.set(directive, hardwareMap.dcMotor.get(dcMotor.value()));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    } else if (clazz == Servo.class) {
                        Servo servo = ((Servo) annotation);
                        ensureType(field, com.qualcomm.robotcore.hardware.Servo.class);
                        try {
                            field.set(directive, hardwareMap.servo.get(servo.value()));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    } else if (clazz == Sensor.class) {
                        Sensor sensor = ((Sensor) annotation);
                        injectSensor(sensor.type(), sensor.value(), field, directive);
                    }
                }
            }
        }
    }

    @Override
    public final void init_loop() {
    }

    @Override
    public final void loop() {
        try {
            for (Map.Entry<GamePadComponent, List<DirectiveFieldPair>> e : gamePadMap1.entrySet()) {
                for (DirectiveFieldPair f : e.getValue()) {
                    injectGamePad(f, e.getKey(), gamepad1);
                }
            }

            for (Map.Entry<GamePadComponent, List<DirectiveFieldPair>> e : gamePadMap2.entrySet()) {
                for (DirectiveFieldPair f : e.getValue()) {
                    injectGamePad(f, e.getKey(), gamepad2);
                }
            }

            for (int i = handlers.size() - 1; i > -1; i--) {
                AbstractDirective d = handlers.get(i);
                switch (d.loop()) {
                    case CHAIN_COMPLETE:
                        d.stop();
                        handlers.remove(i);
                        break;
                    case RUN_NEXT:
                        handlers.set(i, d.getNext());
                        d.getNext().onStart();
                        d.stop();
                        break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void start() {
        for (AbstractDirective d : handlers) {
            d.onStart();
        }
    }

    @Override
    public final void stop() {
        gamePadMap1 = new HashMap<GamePadComponent, List<DirectiveFieldPair>>();
        gamePadMap2 = new HashMap<GamePadComponent, List<DirectiveFieldPair>>();

        handlerStore = new ArrayList<AbstractDirective>();
        handlers = new ArrayList<AbstractDirective>();
        init();
    }

    public final DirectiveChain addDirective(AbstractDirective directive) {
        if (prohibited) {
            throw new IllegalModificationException("Adding handlers after init");
        }
        handlerStore.add(directive);
        handlers.add(directive);
        directive.init(this);
        return new DirectiveChain(this, directive);
    }

    public final String getName() {
        return name;
    }

    protected final CobaltForge setName(String newName) {
        name = newName;
        return this;
    }

    private void injectGamePad(DirectiveFieldPair f, GamePadComponent gpc, Gamepad gamepad) throws IllegalAccessException {
        switch (gpc) {
            case LEFT_STICK_X:
                f.getField().set(f.getObj(), gamepad.left_stick_x);
                break;
            case LEFT_STICK_Y:
                f.getField().set(f.getObj(), gamepad.left_stick_y);
                break;
            case RIGHT_STICK_X:
                f.getField().set(f.getObj(), gamepad.right_stick_x);
                break;
            case RIGHT_STICK_Y:
                f.getField().set(f.getObj(), gamepad.right_stick_y);
                break;
            case DPAD_UP:
                f.getField().set(f.getObj(), gamepad.dpad_up);
                break;
            case DPAD_DOWN:
                f.getField().set(f.getObj(), gamepad.dpad_down);
                break;
            case DPAD_LEFT:
                f.getField().set(f.getObj(), gamepad.dpad_left);
                break;
            case DPAD_RIGHT:
                f.getField().set(f.getObj(), gamepad.dpad_right);
                break;
            case A:
                f.getField().set(f.getObj(), gamepad.a);
                break;
            case B:
                f.getField().set(f.getObj(), gamepad.b);
                break;
            case X:
                f.getField().set(f.getObj(), gamepad.x);
                break;
            case Y:
                f.getField().set(f.getObj(), gamepad.y);
                break;
            case GUIDE:
                f.getField().set(f.getObj(), gamepad.guide);
                break;
            case START:
                f.getField().set(f.getObj(), gamepad.start);
                break;
            case BACK:
                f.getField().set(f.getObj(), gamepad.back);
                break;
            case LEFT_BUMPER:
                f.getField().set(f.getObj(), gamepad.left_bumper);
                break;
            case RIGHT_BUMPER:
                f.getField().set(f.getObj(), gamepad.right_bumper);
                break;
            case LEFT_STICK_BUTTON:
                f.getField().set(f.getObj(), gamepad.left_stick_button);
                break;
            case RIGHT_STICK_BUTTON:
                f.getField().set(f.getObj(), gamepad.right_stick_button);
                break;
            case LEFT_TRIGGER:
                f.getField().set(f.getObj(), gamepad.left_trigger);
                break;
            case RIGHT_TRIGGER:
                f.getField().set(f.getObj(), gamepad.right_trigger);
                break;
        }
    }

    private void ensureType(Field gamePadValField, Class value) {
        if (gamePadValField.getType() != value) {
            throw new IncompatibleInjectionException(gamePadValField, value);
        }
    }

    private void injectSensor(SensorType sensorType, String name, Field field, Object directive) {
        try {
            switch (sensorType) {
                case LEGACY_MODULE:
                    field.set(directive, hardwareMap.legacyModule.get(name));
                    break;
                case TOUCH_SENSOR_MULTIPLEXER:
                    field.set(directive, hardwareMap.touchSensorMultiplexer.get(name));
                    break;
                case DEVICE_INTERFACE_MODULE:
                    field.set(directive, hardwareMap.deviceInterfaceModule.get(name));
                    break;
                case ANALOG_INPUT:
                    field.set(directive, hardwareMap.analogInput.get(name));
                    break;
                case DIGITAL_CHANNEL:
                    field.set(directive, hardwareMap.digitalChannel.get(name));
                    break;
                case OPTICAL_DISTANCE_SENSOR:
                    field.set(directive, hardwareMap.opticalDistanceSensor.get(name));
                    break;
                case TOUCH_SENSOR:
                    field.set(directive, hardwareMap.touchSensor.get(name));
                    break;
                case PWM_OUTPUT:
                    field.set(directive, hardwareMap.pwmOutput.get(name));
                    break;
                case I2C_DEVICE:
                    field.set(directive, hardwareMap.i2cDevice.get(name));
                    break;
                case ANALOG_OUTPUT:
                    field.set(directive, hardwareMap.analogOutput.get(name));
                    break;
                case COLOR_SENSOR:
                    field.set(directive, hardwareMap.colorSensor.get(name));
                    break;
                case LED:
                    field.set(directive, hardwareMap.led.get(name));
                    break;
                case ACCELERATION_SENSOR:
                    field.set(directive, hardwareMap.accelerationSensor.get(name));
                    break;
                case COMPASS_SENSOR:
                    field.set(directive, hardwareMap.compassSensor.get(name));
                    break;
                case GYRO_SENSOR:
                    field.set(directive, hardwareMap.gyroSensor.get(name));
                    break;
                case IR_SEEKER_SENSOR:
                    field.set(directive, hardwareMap.irSeekerSensor.get(name));
                    break;
                case LIGHT_SENSOR:
                    field.set(directive, hardwareMap.lightSensor.get(name));
                    break;
                case ULTRASONIC_SENSOR:
                    field.set(directive, hardwareMap.ultrasonicSensor.get(name));
                    break;
                case VOLTAGE_SENSOR:
                    field.set(directive, hardwareMap.voltageSensor.get(name));
                    break;
                default:
                    throw new IllegalArgumentException("What a terrible failure");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            throw new IncompatibleInjectionException(e);
        }
    }

    final void addHandlerStore(AbstractDirective directive) {
        if (prohibited) {
            throw new IllegalModificationException("Adding handlers after init");
        }
        handlerStore.add(directive);
        directive.init(this);
    }
}

final class DirectiveFieldPair {
    private final AbstractDirective obj;
    private final Field field;

    DirectiveFieldPair(Field field, AbstractDirective obj) {
        this.field = field;
        this.obj = obj;
    }

    final AbstractDirective getObj() {
        return obj;
    }

    final Field getField() {
        return field;
    }
}

final class IncompatibleInjectionException extends RuntimeException {
    IncompatibleInjectionException(Throwable cause) {
        super(cause);
    }

    IncompatibleInjectionException(Field field, Class expectedType) {
        super("Incompatible type for Field: " +
                field.getName() +
                " in " +
                field.getDeclaringClass().getName() +
                ". The type should be " +
                expectedType.getName());
    }
}

final class IllegalModificationException extends RuntimeException {
    IllegalModificationException(String message) {
        super(message);
    }
}
