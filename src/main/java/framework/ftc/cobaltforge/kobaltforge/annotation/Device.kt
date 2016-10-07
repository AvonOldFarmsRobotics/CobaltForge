package framework.ftc.cobaltforge.kobaltforge.annotation


/**
 * Injects the HardwareDevice (only for directives)
 * Created by Dummyc0m on 9/22/16.
 */
@Retention(AnnotationRetention.RUNTIME)
annotation class Device(val value: String = "")
