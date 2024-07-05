package good.damn.tvlist

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Typeface
import android.os.Looper
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import android.os.Handler
import android.util.Log
import android.webkit.WebView
import androidx.annotation.DrawableRes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class App
: Application() {

    companion object {
        private const val TAG = "App"
        private val HANDLER = Handler(
            Looper.getMainLooper()
        )

        lateinit var USER_AGENT: String
        lateinit var RESOURCES: Resources
        const val URL = "http://176.109.108.35"

        val IO = CoroutineScope(
            Dispatchers.IO
        )

        val iconMap = HashMap<String,Bitmap>()

        var WIDTH = 1
        var HEIGHT = 1


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

        Log.d(TAG, "onCreate: USER_AGENT: $USER_AGENT")
    }
}