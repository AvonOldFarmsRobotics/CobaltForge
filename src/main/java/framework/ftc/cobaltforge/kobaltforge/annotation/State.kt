package framework.ftc.cobaltforge.kobaltforge.annotation

/**
 * Be stateful and persistent
 *
 * Supports
 * StringSet
 * String
 * Int
 * Long
 * Float
 * Boolean
 * Created by Dummyc0m on 10/26/16.
 */
@Retention(AnnotationRetention.RUNTIME)
annotation class State(val value: String = "")