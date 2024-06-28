package good.damn.tvlist

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.os.Looper
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import android.os.Handler
import androidx.annotation.DrawableRes

class App
: Application() {

    companion object {

        lateinit var RESOURCES: Resources
        const val URL = "http://176.109.108.35"

        var WIDTH = 1
        var HEIGHT = 1


        private val HANDLER = Handler(
            Looper.getMainLooper()
        )

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
    }
}