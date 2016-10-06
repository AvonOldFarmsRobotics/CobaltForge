package framework.ftc.cobaltforge

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareDevice
import com.qualcomm.robotcore.util.RobotLog
import framework.ftc.cobaltforge.exceptions.IllegalModificationException
import framework.ftc.cobaltforge.exceptions.IncompatibleInjectionException
import java.lang.reflect.Field
import java.util.*

/**
 * CobaltForge OpMode
 * Created by Dummyc0m on 4/7/16.
 */
@SuppressWarnings("WeakerAccess")
@Disabled
abstract class CobaltForge : OpMode() {
    private var gamePadMap1: MutableMap<Component, MutableList<DirectiveFieldPair>> = HashMap()
    private var gamePadMap2: MutableMap<Component, MutableList<DirectiveFieldPair>> = HashMap()

    private var handlerStore: MutableList<AbstractDirective> = ArrayList()
    private var handlers: MutableList<AbstractDirective> = ArrayList()

    val name: String
        get() = _name;
    private var _name = "CobaltForge"
    private var prohibited: Boolean = false

    abstract fun onInit()

    override fun init() {
        Injector.runningCobalt = this

        prohibited = false
        onInit()
        prohibited = true
        for (directive in handlerStore) {
            val fields = directive.javaClass.declaredFields
            for (field in fields) {
                field.isAccessible = true
                val annotations = field.annotations
                for (annotation in annotations) {
                    val clazz = annotation.annotationClass
                    if (clazz == GamePad1::class.java) {
                        val gamePad1 = annotation as GamePad1
                        ensureType(field, gamePad1.value.valueType!!)
                        var fieldList: MutableList<DirectiveFieldPair>? = gamePadMap1[gamePad1.value]
                        if (fieldList == null) {
                            fieldList = ArrayList<DirectiveFieldPair>()
                            gamePadMap1.put(gamePad1.value, fieldList)
                        }
                        fieldList.add(DirectiveFieldPair(field, directive))
                    } else if (clazz == GamePad2::class.java) {
                        val gamePad2 = annotation as GamePad2
                        ensureType(field, gamePad2.value.valueType!!)
                        var fieldList: MutableList<DirectiveFieldPair>? = gamePadMap2[gamePad2.value]
                        if (fieldList == null) {
                            fieldList = ArrayList<DirectiveFieldPair>()
                            gamePadMap2.put(gamePad2.value, fieldList)
                        }
                        fieldList.add(DirectiveFieldPair(field, directive))
                        //                    } else if (clazz == Device.DcMotor.class) {
                        //                        Device.DcMotor dcMotor = ((Device.DcMotor) annotation);
                        //                        ensureType(field, com.qualcomm.robotcore.hardware.DcMotor.class);
                        //                        try {
                        //                            field.set(directive, hardwareMap.dcMotor.get(dcMotor.value()));
                        //                        } catch (IllegalAccessException e) {
                        //                            e.printStackTrace();
                        //                        }
                        //                    } else if (clazz == Device.Servo.class) {
                        //                        Device.Servo servo = ((Device.Servo) annotation);
                        //                        ensureType(field, com.qualcomm.robotcore.hardware.Servo.class);
                        //                        try {
                        //                            field.set(directive, hardwareMap.servo.get(servo.value()));
                        //                        } catch (IllegalAccessException e) {
                        //                            e.printStackTrace();
                        //                        }
                        //                    } else if (clazz == Device.Sensor.class) {
                        //                        Device.Sensor sensor = ((Device.Sensor) annotation);
                        //                        injectSensor(sensor.type(), sensor.value(), field, directive);
                    } else if (clazz == Device::class.java) {
                        val device = annotation as Device
                        injectDevice(device.value, field, directive)
                    } else if (clazz == Inject::class.java) {
                        Injector.injectField(field, directive)
                    }
                }
            }
            directive.onInit()
        }
    }

    override fun init_loop() {
    }

    override fun loop() {
        try {
            for ((key, value) in gamePadMap1) {
                for (f in value) {
                    injectGamePad(f, key, gamepad1)
                }
            }

            for ((key, value) in gamePadMap2) {
                for (f in value) {
                    injectGamePad(f, key, gamepad2)
                }
            }

            for (i in handlers.size - 1 downTo -1 + 1) {
                val d = handlers[i]
                when (d.loop()) {
                    ExecutionResult.CHAIN_COMPLETE -> {
                        d.stop()
                        handlers.removeAt(i)
                    }
                    ExecutionResult.RUN_NEXT -> {
                        handlers[i] = d.next!!
                        d.next!!.onStart()
                        d.stop()
                    }
                    else -> {
                        RobotLog.a(d.toString() + " Running");
                    }
                }
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }

    override fun start() {
        for (d in handlers) {
            d.onStart()
        }
    }

    override fun stop() {
        gamePadMap1 = HashMap<Component, MutableList<DirectiveFieldPair>>()
        gamePadMap2 = HashMap<Component, MutableList<DirectiveFieldPair>>()

        handlerStore = ArrayList<AbstractDirective>()
        handlers = ArrayList<AbstractDirective>()
        init()
    }

    fun addDirective(directive: AbstractDirective): DirectiveChain {
        if (prohibited) {
            throw IllegalModificationException("Adding handlers after init")
        }
        handlerStore.add(directive)
        handlers.add(directive)
        directive.init(this)
        return DirectiveChain(this, directive)
    }

    protected fun setName(newName: String): CobaltForge {
        _name = newName
        return this
    }

    @Throws(IllegalAccessException::class)
    private fun injectGamePad(f: DirectiveFieldPair, gpc: Component, gamepad: Gamepad) {
        when (gpc) {
            Component.LEFT_STICK_X -> f.field.set(f.obj, gamepad.left_stick_x)
            Component.LEFT_STICK_Y -> f.field.set(f.obj, gamepad.left_stick_y)
            Component.RIGHT_STICK_X -> f.field.set(f.obj, gamepad.right_stick_x)
            Component.RIGHT_STICK_Y -> f.field.set(f.obj, gamepad.right_stick_y)
            Component.DPAD_UP -> f.field.set(f.obj, gamepad.dpad_up)
            Component.DPAD_DOWN -> f.field.set(f.obj, gamepad.dpad_down)
            Component.DPAD_LEFT -> f.field.set(f.obj, gamepad.dpad_left)
            Component.DPAD_RIGHT -> f.field.set(f.obj, gamepad.dpad_right)
            Component.A -> f.field.set(f.obj, gamepad.a)
            Component.B -> f.field.set(f.obj, gamepad.b)
            Component.X -> f.field.set(f.obj, gamepad.x)
            Component.Y -> f.field.set(f.obj, gamepad.y)
            Component.GUIDE -> f.field.set(f.obj, gamepad.guide)
            Component.START -> f.field.set(f.obj, gamepad.start)
            Component.BACK -> f.field.set(f.obj, gamepad.back)
            Component.LEFT_BUMPER -> f.field.set(f.obj, gamepad.left_bumper)
            Component.RIGHT_BUMPER -> f.field.set(f.obj, gamepad.right_bumper)
            Component.LEFT_STICK_BUTTON -> f.field.set(f.obj, gamepad.left_stick_button)
            Component.RIGHT_STICK_BUTTON -> f.field.set(f.obj, gamepad.right_stick_button)
            Component.LEFT_TRIGGER -> f.field.set(f.obj, gamepad.left_trigger)
            Component.RIGHT_TRIGGER -> f.field.set(f.obj, gamepad.right_trigger)
        }
    }

    private fun ensureType(gamePadValField: Field, value: Class<*>) {
        if (gamePadValField.type != value) {
            throw IncompatibleInjectionException(gamePadValField, value)
        }
    }

    private fun injectDevice(name: String, field: Field, directive: AbstractDirective) {
        var localName = name
        try {
            if ("" == localName) {
                localName = field.name
            }
            val device = hardwareMap.get(field.type as Class<out HardwareDevice>, localName)
            field.set(directive, device)
        } catch (e: IllegalArgumentException) {
            throw IncompatibleInjectionException(e)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: ClassCastException) {
            throw IncompatibleInjectionException(field, HardwareDevice::class.java)
        }

    }

    //    @Deprecated
    //    private void injectSensor(SensorType sensorType, String name, Field field, Object directive) {
    //        try {
    //            switch (sensorType) {
    //                case LEGACY_MODULE:
    //                    field.set(directive, hardwareMap.legacyModule.get(name));
    //                    break;
    //                case TOUCH_SENSOR_MULTIPLEXER:
    //                    field.set(directive, hardwareMap.touchSensorMultiplexer.get(name));
    //                    break;
    //                case DEVICE_INTERFACE_MODULE:
    //                    field.set(directive, hardwareMap.deviceInterfaceModule.get(name));
    //                    break;
    //                case ANALOG_INPUT:
    //                    field.set(directive, hardwareMap.analogInput.get(name));
    //                    break;
    //                case DIGITAL_CHANNEL:
    //                    field.set(directive, hardwareMap.digitalChannel.get(name));
    //                    break;
    //                case OPTICAL_DISTANCE_SENSOR:
    //                    field.set(directive, hardwareMap.opticalDistanceSensor.get(name));
    //                    break;
    //                case TOUCH_SENSOR:
    //                    field.set(directive, hardwareMap.touchSensor.get(name));
    //                    break;
    //                case PWM_OUTPUT:
    //                    field.set(directive, hardwareMap.pwmOutput.get(name));
    //                    break;
    //                case I2C_DEVICE:
    //                    field.set(directive, hardwareMap.i2cDevice.get(name));
    //                    break;
    //                case ANALOG_OUTPUT:
    //                    field.set(directive, hardwareMap.analogOutput.get(name));
    //                    break;
    //                case COLOR_SENSOR:
    //                    field.set(directive, hardwareMap.colorSensor.get(name));
    //                    break;
    //                case LED:
    //                    field.set(directive, hardwareMap.led.get(name));
    //                    break;
    //                case ACCELERATION_SENSOR:
    //                    field.set(directive, hardwareMap.accelerationSensor.get(name));
    //                    break;
    //                case COMPASS_SENSOR:
    //                    field.set(directive, hardwareMap.compassSensor.get(name));
    //                    break;
    //                case GYRO_SENSOR:
    //                    field.set(directive, hardwareMap.gyroSensor.get(name));
    //                    break;
    //                case IR_SEEKER_SENSOR:
    //                    field.set(directive, hardwareMap.irSeekerSensor.get(name));
    //                    break;
    //                case LIGHT_SENSOR:
    //                    field.set(directive, hardwareMap.lightSensor.get(name));
    //                    break;
    //                case ULTRASONIC_SENSOR:
    //                    field.set(directive, hardwareMap.ultrasonicSensor.get(name));
    //                    break;
    //                case VOLTAGE_SENSOR:
    //                    field.set(directive, hardwareMap.voltageSensor.get(name));
    //                    break;
    //                default:
    //                    throw new IllegalArgumentException("What a terrible failure");
    //            }
    //        } catch (IllegalAccessException e) {
    //            e.printStackTrace();
    //        } catch (IllegalArgumentException e) {
    //            throw new IncompatibleInjectionException(e);
    //        }
    //    }

    internal fun addHandlerStore(directive: AbstractDirective) {
        if (prohibited) {
            throw IllegalModificationException("Adding handlers after init")
        }
        handlerStore.add(directive)
        directive.init(this)
    }
}