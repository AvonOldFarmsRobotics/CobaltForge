package framework.ftc.cobaltforge.exceptions

import java.lang.reflect.Field

/**
 * thrown when the field and the field type are not compatible
 * Created by Dummyc0m on 9/22/16.
 */
class IncompatibleInjectionException : RuntimeException {
    constructor(cause: Throwable) : super(cause) {
    }

    constructor(field: Field, expectedType: Class<*>) : super("Incompatible type for Field: " +
            field.name +
            " in " +
            field.declaringClass.name +
            ". The type should be " +
            expectedType.name) {
    }
}
