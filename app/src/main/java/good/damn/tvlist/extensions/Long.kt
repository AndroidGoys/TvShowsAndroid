package good.damn.tvlist.extensions

import good.damn.tvlist.App

fun Long.toShareChannelUrl(
    withTimeStartSec: Int = App.CURRENT_TIME_SECONDS
) = "${App.URL_SHARING_CHANNEL}/$this?" +
    "time-start=$withTimeStartSec&" +
    "time-zone=3"