package good.damn.tvlist.extensions

import androidx.annotation.ColorInt
import java.util.Calendar

fun Int.normalWidth() = this / 414f
fun Int.normalHeight() = this / 915f

fun Int.toGregorianDateString(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this * 1000L

    val day = calendar.get(
        Calendar.DAY_OF_MONTH
    )

    val month = calendar.get(
        Calendar.MONTH
    ) + 1

    val year = calendar.get(
        Calendar.YEAR
    )

    return "$day.${month / 10}${month % 10}.$year"
}

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