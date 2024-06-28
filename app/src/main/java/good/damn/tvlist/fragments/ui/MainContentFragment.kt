package good.damn.tvlist.fragments.ui

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.withAlpha
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.views.RoundedImageView

class MainContentFragment
: StackFragment() {

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
        val searchView = View(
            context
        )
        val imageViewLikes = RoundedImageView(
            context
        )



        // Background colors
        //
        layout.setBackgroundColorId(
            R.color.background
        )
        layoutTopBar.setBackgroundColor(0)
        layoutTopBarContent.setBackgroundColor(
            App.color(
                R.color.background
            ).withAlpha(0.5f)
        )
        imageViewLikes.setBackgroundColor(
            0xffff0000.toInt()
        )
        searchView.setBackgroundColor(
            0xffff0000.toInt()
        )
        imageViewProfile.setBackgroundColor(
            0xffff0000.toInt()
        )
        bottomNavigationView.setBackgroundColor(
            0xffff0000.toInt()
        )
        // Stroke colors
        imageViewProfile.setStrokeColorId(
            R.color.lime
        )
        imageViewLikes.strokeColor = 0x3318191F




        // Drawables
        imageViewProfile.drawable = App.drawable(
            R.drawable.ic
        )
        imageViewLikes.drawable = App.drawable(
            R.drawable.ic_heart
        )
        imageViewLikes.imageScaleX = 0.5f
        imageViewLikes.imageScaleY = 0.5f



        // Bounds
        layoutTopBar.boundsFrame(
            Gravity.TOP,
            width = -1,
            height = (measureUnit * 0.1715f).toInt()
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


        return layout
    }
}