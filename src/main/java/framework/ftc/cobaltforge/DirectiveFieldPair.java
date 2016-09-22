package framework.ftc.cobaltforge;

import java.lang.reflect.Field;

/**
 * Internal
 * Created by Dummyc0m on 9/22/16.
 */
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
