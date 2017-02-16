package framework.ftc.cobaltforge.kobaltforge.util

/**
 * Created by Dummyc0m on 2/4/17.
 */
class Listener<T>(defaultValue: T) {
    private var prevValue = defaultValue
    private var consumer: ((T, T) -> Unit)? = null

    fun newValue(newValue: T) {
        if (prevValue != newValue) {
            consumer?.invoke(prevValue, newValue)
            prevValue = newValue
        }
    }

    fun onChange(consumer: (T, T) -> Unit) {
        this.consumer = consumer
    }
}