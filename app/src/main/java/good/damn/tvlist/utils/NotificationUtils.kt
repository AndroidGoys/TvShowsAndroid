package good.damn.tvlist.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import good.damn.tvlist.broadcast_receivers.notificaition.TVShowScheduleReceiver
import good.damn.tvlist.extensions.getAlarmManager
import java.util.Calendar

class NotificationUtils {
    companion object {
        fun scheduleNotification(
            context: Context,
            title: String,
            largeText: String,
            atTime: Long
        ) {
            val intent = Intent(
                context,
                TVShowScheduleReceiver::class.java
            ).apply {

                putExtra(
                    TVShowScheduleReceiver.KEY_TITLE,
                    title
                )

                putExtra(
                    TVShowScheduleReceiver.KEY_TEXT,
                    largeText
                )
            }


            val pending = PendingIntent.getBroadcast(
                context,
                1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or (
                    PendingIntent.FLAG_IMMUTABLE
                )
            )

            val alarm = context.getAlarmManager()
            alarm.set(
                AlarmManager.RTC_WAKEUP,
                atTime,
                pending
            )
        }
    }

}