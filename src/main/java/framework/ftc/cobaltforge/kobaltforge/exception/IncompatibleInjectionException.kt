package framework.ftc.cobaltforge.kobaltforge.exception

import kotlin.reflect.KMutableProperty1

/**
 * thrown when the field and the field type are not compatible
 * Created by Dummyc0m on 9/22/16.
 */
class IncompatibleInjectionException : RuntimeException {
    constructor(cause: Throwable) : super(cause) {
    }

    constructor(field: KMutableProperty1<Any, Any>, expectedType: Class<*>) : super("Incompatible type for Field: " +
            field.name +
            " should be " +
            expectedType.name) {
    }
}
