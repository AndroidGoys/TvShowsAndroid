package good.damn.tvlist.broadcast_receivers.notificaition

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.getNotificationManager

class TVShowScheduleReceiver
: BroadcastReceiver() {

    companion object {
        const val KEY_TITLE = "title"
        const val KEY_TEXT = "text"
    }

    override fun onReceive(
        context: Context?,
        intent: Intent?
    ) {
        if (context == null || intent == null) {
            return
        }

        val title = intent.getStringExtra(KEY_TITLE)
        val largeText = intent.getStringExtra(KEY_TEXT)

        val notification = NotificationCompat.Builder(
            context,
            App.NOTIFICATION_ID
        ).setSmallIcon(
            R.drawable.ic_play_fill
        ).setContentText(
            largeText
        ).setContentTitle(
            title
        ).build()

        context.getNotificationManager()
            .notify(1,notification)

    }
}