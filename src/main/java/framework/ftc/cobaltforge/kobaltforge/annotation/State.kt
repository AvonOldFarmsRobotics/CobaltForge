package framework.ftc.cobaltforge.kobaltforge.annotation

/**
 * Be stateful and persistent
 *
 * Supports
 * String default ""
 * Int default 0
 * Long
 * Float
 * Double
 * Boolean default false
 * Created by Dummyc0m on 10/26/16.
 */
@Retention(AnnotationRetention.RUNTIME)
annotation class State(val value: String = "")