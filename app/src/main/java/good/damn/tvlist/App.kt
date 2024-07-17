package good.damn.tvlist

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Looper
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import android.os.Handler
import android.util.Log
import android.webkit.WebView
import androidx.annotation.DrawableRes
import good.damn.tvlist.extensions.getNotificationManager
import good.damn.tvlist.network.api.models.TVProgram
import good.damn.tvlist.network.api.models.auth.TokenAuth
import good.damn.tvlist.utils.BuildUtils
import good.damn.tvlist.utils.NotificationUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.io.File

class App
: Application() {

    companion object {
        private const val TAG = "App"
        private val HANDLER = Handler(
            Looper.getMainLooper()
        )

        var TOKEN_AUTH: TokenAuth? = null

        lateinit var USER_AGENT: String
        lateinit var RESOURCES: Resources
        lateinit var CACHE_DIR: File
        const val URL = "http://176.109.106.211:8080"
        const val NOTIFICATION_ID = "tvShowSchedule"

        val IO = CoroutineScope(
            Dispatchers.IO
        )

        val FAVOURITE_TV_SHOWS = HashMap<Long, TVProgram>()
        val iconMap = HashMap<String, Bitmap>()
        val mediumBitmaps = HashMap<String, Bitmap>()

        var WIDTH = 1
        var HEIGHT = 1

        var NETWORK_AVAILABLE = false

        fun ui(
            runnable: Runnable
        ) {
            HANDLER.post(runnable)
        }

        fun font(
            @FontRes id: Int,
            context: Context
        ) = ResourcesCompat.getFont(
            context,
            id
        )

        fun drawable(
            @DrawableRes id: Int
        ) = ResourcesCompat.getDrawable(
            RESOURCES,
            id,
            null
        )

        @ColorInt
        fun color(
            @ColorRes id: Int
        ) = ResourcesCompat.getColor(
            RESOURCES,
            id,
            null
        )

    }

    override fun onCreate() {
        super.onCreate()

        RESOURCES = resources

        USER_AGENT = WebView(applicationContext)
            .settings
            .userAgentString

        CACHE_DIR = applicationContext
            .cacheDir

        Log.d(TAG, "onCreate: USER_AGENT: $USER_AGENT")

        createNotificationChannels()

    }
}

private fun App.createNotificationChannels() {
    if (!BuildUtils.isOreo26()) {
        return
    }

    val manager = applicationContext
        .getNotificationManager()

    if (manager
        .notificationChannels
        .isNotEmpty()
    ) {
        return
    }

    NotificationUtils.createNotificationChannel(
        App.NOTIFICATION_ID,
        getString(R.string.nt_schedule),
        getString(R.string.nd_schedule),
        applicationContext
    )

}