package good.damn.tvlist.utils

import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import good.damn.tvlist.broadcast_receivers.notificaition.TVShowScheduleReceiver
import good.damn.tvlist.extensions.getAlarmManager
import good.damn.tvlist.extensions.getNotificationManager
import java.util.Calendar

class NotificationUtils {
    companion object {
        private const val TAG = "NotificationUtils"
        @RequiresApi(api = Build.VERSION_CODES.O)
        fun createNotificationChannel(
            id: String,
            name: String,
            desc: String,
            context: Context
        ) {
            NotificationChannel(
                id,
                name,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = desc
                context.getNotificationManager()
                    .createNotificationChannel(
                        this
                    )
            }

        }

        fun scheduleNotification(
            context: Context,
            notificationId: Int,
            title: String,
            largeText: String,
            atTime: Long,
            imageUrl: String? = null,
            dirName: String? = null
        ) {
            val intent = Intent(
                context,
                TVShowScheduleReceiver::class.java
            ).apply {

                putExtra(
                    TVShowScheduleReceiver.KEY_ID,
                    notificationId
                )

                putExtra(
                    TVShowScheduleReceiver.KEY_TITLE,
                    title
                )

                putExtra(
                    TVShowScheduleReceiver.KEY_TEXT,
                    largeText
                )

                putExtra(
                    TVShowScheduleReceiver.KEY_DIR_NAME,
                    dirName
                )

                putExtra(
                    TVShowScheduleReceiver.KEY_IMAGE_URL,
                    imageUrl
                )
            }


            val pending = PendingIntent.getBroadcast(
                context,
                notificationId,
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