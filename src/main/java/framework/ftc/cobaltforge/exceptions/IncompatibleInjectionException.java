package framework.ftc.cobaltforge.exceptions;

import java.lang.reflect.Field;

/**
 * thrown when the field and the field type are not compatible
 * Created by Dummyc0m on 9/22/16.
 */
public final class IncompatibleInjectionException extends RuntimeException {
    public IncompatibleInjectionException(Throwable cause) {
        super(cause);
    }

    public IncompatibleInjectionException(Field field, Class expectedType) {
        super("Incompatible type for Field: " +
                field.getName() +
                " in " +
                field.getDeclaringClass().getName() +
                ". The type should be " +
                expectedType.getName());
    }
}
