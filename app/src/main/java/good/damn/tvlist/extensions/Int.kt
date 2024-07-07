package good.damn.tvlist.extensions

import androidx.annotation.ColorInt

fun Int.rgba(): FloatArray {
    val a = this shr 24 and 0xff
    val r = this shr 16 and 0xff
    val g = this shr 8 and 0xff
    val b = this and 0xff

    return floatArrayOf(
        r / 255f,
        g / 255f,
        b / 255f,
        a / 255f
    )
}

@ColorInt
fun Int.withAlpha(
    alpha: Float
): Int {
    return ((255 * alpha).toInt() and 0xff shl 24) or (
        this and 0x00ffffff
    )
}