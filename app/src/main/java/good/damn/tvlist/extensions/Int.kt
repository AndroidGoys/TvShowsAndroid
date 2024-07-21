package good.damn.tvlist.extensions

import androidx.annotation.ColorInt
import java.util.Calendar
import java.util.Date

fun Int.normalWidth() = this / 414f
fun Int.normalHeight() = this / 915f

fun Int.toTimeString(): String {
    val c = Calendar.getInstance()
    c.time = Date(this * 1000L)

    val hour = c.get(
        Calendar.HOUR_OF_DAY
    )

    val mins = c.get(
        Calendar.MINUTE
    )

    val hourString = "${hour / 10}${hour % 10}"
    val minutesString = "${mins/10}${mins % 10}"

    return "$hourString:$minutesString"
}

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