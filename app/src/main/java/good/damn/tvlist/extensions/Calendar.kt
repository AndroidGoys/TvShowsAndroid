package good.damn.tvlist.extensions

import java.util.Calendar

fun Calendar.getTimeInSeconds() = (
    timeInMillis / 1000L
).toInt()

fun Calendar.getMonthString(): String {
    val month = get(
        Calendar.MONTH
    ) + 1

    val month1 = month / 10
    val month2 = month % 10

    return "$month1$month2"
}

fun Calendar.getDayString(): String {
    val day = get(
        Calendar.DAY_OF_MONTH
    )

    val day1 = day / 10
    val day2 = day % 10

    return "$day1$day2"
}