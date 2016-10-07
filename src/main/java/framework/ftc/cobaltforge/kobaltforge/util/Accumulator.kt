package framework.ftc.cobaltforge.kobaltforge.util

/**
 * Generic helper to accumulate any value
 * Created by Dummyc0m on 10/6/16.
 */
class Accumulator<V : Any>(private var value: V,
                           private val accumulate: (toAccumulate: V, originalValue: V) -> V) {

    fun accumulate(value: V) {
        this.value = accumulate(value, this.value)
    }

    fun consume(consumer: (value: V) -> Unit) {
        consumer(value)
    }
}