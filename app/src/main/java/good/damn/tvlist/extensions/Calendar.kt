package good.damn.tvlist.extensions

import java.util.Calendar

fun Calendar.getTimeInSeconds() = (
    timeInMillis / 1000L
).toInt()

fun Calendar.getDateTimeString() =
    "${getDayString()}.${getMonthString()}.${get(Calendar.YEAR)} " +
    "${getHourString()}:${getMinuteString()}"

fun Calendar.getMinuteString() = get2NumberString(
    Calendar.MINUTE
)

fun Calendar.getHourString() = get2NumberString(
    Calendar.HOUR_OF_DAY
)

fun Calendar.getMonthString(): String {
    val month = get(
        Calendar.MONTH
    ) + 1

    val month1 = month / 10
    val month2 = month % 10

    return "$month1$month2"
}

fun Calendar.getDayString() = get2NumberString(
    Calendar.DAY_OF_MONTH
)

private fun Calendar.get2NumberString(
    field: Int
): String {
    val v = get(field)

    val v1 = v / 10
    val v2 = v % 10

    return "$v1$v2"
}