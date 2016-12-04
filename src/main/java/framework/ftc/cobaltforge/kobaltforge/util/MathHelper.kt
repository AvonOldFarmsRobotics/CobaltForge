package framework.ftc.cobaltforge.kobaltforge.util

import java.util.*

/**
 * does math, that's it
 * Created by Dummyc0m on 12/3/16.
 */
object MathHelper {
    private val SIN_TABLE = FloatArray(65536)
    private val random = Random()

    init {
        for (var0 in 0..65536) {
            SIN_TABLE[var0] = Math.sin(var0.toDouble() * Math.PI * 2.0 / 65536.0).toFloat()
        }
    }

    fun random(min: Int, max: Int): Int {
        return random.nextInt(max - min) + min
    }

    fun random(min: Double, max: Double): Double {
        return (random.nextDouble() + +java.lang.Double.MIN_VALUE) * (max - min) + min
    }

    fun sin(theta: Float): Float {
        return SIN_TABLE[(theta * 10430.378f).toInt() and 65535]
    }

    fun cos(theta: Float): Float {
        return SIN_TABLE[(theta * 10430.378f + 16384.0f).toInt() and 65535]
    }

    fun abs(value: Float): Float {
        return if (value >= 0.0f) value else -value
    }

    fun abs_int(value: Int): Int {
        return if (value >= 0) value else -value
    }

    fun dotProduct(a: Double, b: Double, theta: Double): Double {
        return a * b * Math.cos(theta)
    }

    fun magnitude(x: Double, y: Double): Double {
        return Math.sqrt(x * x + y * y)
    }

    fun isInteger(str: String?): Boolean {
        if (str == null) {
            return false
        }
        val length = str.length
        if (length == 0) {
            return false
        }
        var i = 0
        if (str[0] == '-') {
            if (length == 1) {
                return false
            }
            i = 1
        }
        while (i < length) {
            val c = str[i]
            if (c < '0' || c > '9') {
                return false
            }
            i++
        }
        return true
    }
}

fun Float.sin(): Float {
    return MathHelper.sin(this)
}

fun Float.cos(): Float {
    return MathHelper.cos(this)
}

fun Double.sin(): Double {
    return MathHelper.cos(this.toFloat()).toDouble()
}

fun Double.cos(): Double {
    return MathHelper.cos(this.toFloat()).toDouble()
}

fun Double.abs(): Double {
    return if (this >= 0.0) this else -this
}

fun Float.abs(): Float {
    return if (this >= 0.0f) this else -this
}

fun Int.abs(): Int {
    return if (this >= 0) this else -this
}

fun Double.sqrt(): Double {
    return Math.sqrt(this)
}

fun Float.sqrt(): Float {
    return Math.sqrt(this.toDouble()).toFloat()
}

fun Int.sqrt(): Int {
    return Math.sqrt(this.toDouble()).toInt()
}

fun String.isInteger(): Boolean {
    val length = this.length
    if (length == 0) {
        return false
    }
    var i = 0
    if (this[0] == '-') {
        if (length == 1) {
            return false
        }
        i = 1
    }
    while (i < length) {
        val c = this[i]
        if (c < '0' || c > '9') {
            return false
        }
        i++
    }
    return true
}