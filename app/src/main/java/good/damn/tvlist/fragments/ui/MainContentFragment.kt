package good.damn.tvlist.fragments.ui

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.BlurMaskFilter.Blur
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.activities.MainActivity
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.extensions.withAlpha
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.views.SearchView
import good.damn.tvlist.views.round.RoundedImageView

class MainContentFragment
: StackFragment() {

    companion object {
        private const val TAG = "MainContentFragment"
    }
    
    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {
        val layout = FrameLayout(
            context
        )
        val viewPager = ViewPager2(
            context
        )
        val bottomNavigationView = View(
            context
        )

        val layoutTopBar = FrameLayout(
            context
        )
        val layoutTopBarContent = LinearLayout(
            context
        )
        val imageViewProfile = RoundedImageView(
            context
        )
        val searchView = SearchView(
            context
        )
        val imageViewLikes = RoundedImageView(
            context
        )


        // Background colors
        layout.setBackgroundColorId(
            R.color.background
        )
        layoutTopBar.setBackgroundColor(0)
        layoutTopBarContent.setBackgroundColor(
            App.color(
                R.color.background
            ).withAlpha(0.5f)
        )
        searchView.setBackgroundColorId(
            R.color.searchViewBack
        )
        bottomNavigationView.setBackgroundColor(
            0xffff0000.toInt()
        )
        // Stroke colors
        imageViewProfile.setStrokeColorId(
            R.color.lime
        )
        imageViewLikes.strokeColor = 0x3318191F
        // Text color
        App.color(
            R.color.searchText
        ).withAlpha(0.39f).let {
            searchView.textColorWord = it
            searchView.textColorExample = it
        }

        // Typefaces
        searchView.typefaceExample = App.font(
            R.font.open_sans_regular,
            context
        )
        searchView.typefaceWord = App.font(
            R.font.open_sans_bold,
            context
        )

        // Text
        searchView.textExample = getString(
            R.string.for_example
        )
        searchView.textWords = arrayOf(
            "стс",
            "start",
            "вверх",
            "чебурашка"
        )


        // Text size
        searchView.textSizeWord = 0.29268f
        searchView.textSizeExample = 0.29268f

        // Icon Size
        searchView.iconSize = 0.4878f
        searchView.iconPaddingRight = 0.041353f

        // Search view animation config
        searchView.animationDuration = 750
        searchView.animationInterval = 4000
        searchView.animationInterpolator = LinearInterpolator()

        // Drawables
        imageViewProfile.drawable = App.drawable(
            R.drawable.ic
        )
        App.drawable(
            R.drawable.ic_lens
        )?.let {
            it.alpha = (255 * 0.48f).toInt()
            searchView.drawable = it
        }
        imageViewLikes.drawable = App.drawable(
            R.drawable.ic_heart
        )
        imageViewLikes.imageScaleX = 0.5f
        imageViewLikes.imageScaleY = 0.5f



        // Bounds
        layoutTopBar.boundsFrame(
            Gravity.TOP,
            width = -1,
            height = (measureUnit * 0.1715f).toInt() + getTopInset()
        )
        layoutTopBarContent.boundsFrame(
            Gravity.CENTER
        )
        (measureUnit * 0.09903f).toInt().let {
            imageViewProfile.boundsLinear(
                width = it,
                height = it,
            )

            (it * 0.04878f).let { strokeWidth ->
                imageViewProfile.strokeWidth = strokeWidth
                imageViewLikes.strokeWidth = strokeWidth
            }

            imageViewProfile.cornerRadius = it * 0.5f
            imageViewLikes.cornerRadius = it * 0.5f
            searchView.cornerRadius = it * 0.2317f

            (measureUnit * 0.04348f).let { interval ->
                searchView.boundsLinear(
                    width = (measureUnit * 0.64251f).toInt(),
                    height = it,
                    left = interval
                )
                imageViewLikes.boundsLinear(
                    width = it,
                    height = it,
                    left = interval
                )
            }
            searchView.setPadding(
                (searchView.widthParams() * 0.04887f).toInt(),
                0,
                0,
                0
            )
        }

        (measureUnit * 0.13285f).toInt().let { bottomHeight ->
            bottomNavigationView.boundsFrame(
                Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL,
                width = (measureUnit * 0.44202f).toInt(),
                height = bottomHeight,
                bottom = (measureUnit * 0.11352f)
            )
        }

        layoutTopBarContent.apply {
            addView(imageViewProfile)
            addView(searchView)
            addView(imageViewLikes)
        }

        layoutTopBar.addView(
            layoutTopBarContent
        )

        layout.apply {
            addView(viewPager)
            addView(layoutTopBar)
            addView(bottomNavigationView)
        }

        searchView.startAnimation()

        return layout
    }
}