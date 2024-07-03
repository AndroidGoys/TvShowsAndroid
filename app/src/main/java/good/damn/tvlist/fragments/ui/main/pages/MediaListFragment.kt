package good.damn.tvlist.fragments.ui.main.pages

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.fragments.IOSNavigationFragment
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.buttons.BigButtonView

class MediaListFragment
: IOSNavigationFragment() {

    override fun onContentView(
        context: Context,
        measureUnit: Int
    ): View {
        val layout = ViewUtils.verticalLinear(
            context
        )

        val categories = arrayOf(
            Category(
                R.drawable.ic_films,
                R.string.films
            ),
            Category(
                R.drawable.ic_series,
                R.string.series
            ),
            Category(
                R.drawable.ic_cartoons,
                R.string.cartoons
            ),
            Category(
                R.drawable.ic_sport,
                R.string.sport
            ),
            Category(
                R.drawable.ic_audiobooks,
                R.string.audiobooks
            ),
            Category(
                R.drawable.ic_trailers,
                R.string.trailers
            )
        )

        val textColor = App.color(
            R.color.accentColor
        )

        val semibold = App.font(
            R.font.open_sans_semi_bold,
            context
        )

        val interval = measureUnit * 0.04106f
        val drawableArrow = App.drawable(
            R.drawable.ic_arrow_forward
        )

        val buttonWidth = (measureUnit * 0.9227f).toInt()
        val buttonHeight = (measureUnit * 0.18357f).toInt()

        val cornerRadius = buttonHeight * 0.18421f
        val elevation = buttonHeight * 0.04f

        for (i in categories.indices) {
            val it = categories[i]
            val button = BigButtonView(
                context
            )

            button.textColor = textColor
            button.typeface = semibold

            button.imageStartSizeFactor = 0.38486f
            button.imageEndSizeFactor = 0.27684f
            button.textSizeFactor = 0.22368f

            button.imageStart = App.drawable(
                it.drawId
            )

            button.text = getString(
                it.stringId
            )

            button.imageEnd = drawableArrow


            button.boundsLinear(
                Gravity.CENTER_HORIZONTAL,
                width = buttonWidth,
                height = buttonHeight,
                top = interval,
                bottom = if (i == categories.size-1)
                    interval else 0f
            )

            button.radius = cornerRadius
            button.cardElevation = elevation

            layout.addView(
                button
            )
        }

        layout.boundsLinear(
            width = -1,
            height = -1,
            top = measureUnit * 0.05797f
        )

        return layout
    }

}

private data class Category(
    @DrawableRes val drawId: Int,
    @StringRes val stringId: Int
)