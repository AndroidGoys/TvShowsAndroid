package good.damn.tvlist.broadcast_receivers.notificaition

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.cache.CacheBitmap
import good.damn.tvlist.extensions.getNotificationManager
import good.damn.tvlist.network.bitmap.NetworkBitmap

class TVShowScheduleReceiver
: BroadcastReceiver() {

    companion object {
        private const val TAG = "TVShowScheduleReceiver"
        const val KEY_ID = "notifyId"
        const val KEY_TITLE = "title"
        const val KEY_TEXT = "text"
        const val KEY_IMAGE_URL = "preview"
        const val KEY_DIR_NAME = "dirName"
    }

    override fun onReceive(
        context: Context?,
        intent: Intent?
    ) {
        if (context == null || intent == null) {
            return
        }

        val id = intent.getIntExtra(KEY_ID, 1)
        val title = intent.getStringExtra(KEY_TITLE)
        val largeText = intent.getStringExtra(KEY_TEXT)
        val imageUrl = intent.getStringExtra(KEY_IMAGE_URL)
        val dirName = intent.getStringExtra(KEY_DIR_NAME)

        val notification = NotificationCompat.Builder(
            context,
            App.NOTIFICATION_ID
        ).setSmallIcon(
            R.drawable.ic_play_fill
        ).setContentText(
            largeText
        ).setContentTitle(
            title
        ).setStyle(
            NotificationCompat.BigTextStyle()
                .bigText(largeText)
                .setBigContentTitle(title)
        ).setChannelId(
            App.NOTIFICATION_ID
        )

        imageUrl?.let { url ->
            dirName ?: return
            NetworkBitmap.loadFromNetwork(
                url,
                context.cacheDir,
                dirName,
                204,
                253
            ) {
                context.getNotificationManager()
                    .notify(id, notification
                        .setLargeIcon(it)
                        .build())
            }
        }

        context.getNotificationManager()
            .notify(id, notification.build())
    }
}