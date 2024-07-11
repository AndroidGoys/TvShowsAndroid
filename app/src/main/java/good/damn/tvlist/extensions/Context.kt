package good.damn.tvlist.extensions

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager

fun Context.getConnectivityManager() = getSystemService(
    Context.CONNECTIVITY_SERVICE
) as ConnectivityManager

fun Context.getNotificationManager() = getSystemService(
    Context.NOTIFICATION_SERVICE
) as NotificationManager

fun Context.getAlarmManager() = getSystemService(
    Context.ALARM_SERVICE
) as AlarmManager