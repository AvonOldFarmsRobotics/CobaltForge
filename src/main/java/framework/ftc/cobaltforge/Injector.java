package framework.ftc.cobaltforge;

import framework.ftc.cobaltforge.exceptions.IncompatibleInjectionException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dummyc0m on 9/28/16.
 */
class Injector {
    static CobaltForge runningCobalt;
    private static Injector ourInstance = new Injector();
    private Map<Class<Object>, Object> injectableMap = new HashMap<Class<Object>, Object>();

    private Injector() {
    }

    static Injector getInstance() {
        return ourInstance;
    }

    private Object getInjectable(Class<Object> clazz) throws IllegalAccessException, InstantiationException {
        return injectableMap.containsKey(clazz) ? injectableMap.get(clazz) : clazz.newInstance();
    }

    private void injectObject(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                Class clazz = annotation.annotationType();
                if (clazz == Inject.class) {
                    injectField(field, object);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    void injectField(Field field, Object directive) {
        try {
            Class clazz = field.getType();
            if (clazz == CobaltForge.class) {
                System.out.println("Injected CobaltForge");
                field.set(directive, runningCobalt);
            } else {
                Object object = getInjectable(clazz);
                injectObject(object);
                System.out.println("Injected " + object);
                field.set(directive, object);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            throw new IncompatibleInjectionException(e);
        } catch (ClassCastException e) {
            throw new IncompatibleInjectionException(field, Object.class);
        }
    }
}
