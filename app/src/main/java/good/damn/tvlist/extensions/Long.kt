package good.damn.tvlist.extensions

import good.damn.tvlist.App

fun Long.toShareChannelUrl() = "${App.URL_SHARING_CHANNEL}/$this?" +
    "time-start=${App.CURRENT_TIME_SECONDS}&" +
    "time-zone=3"