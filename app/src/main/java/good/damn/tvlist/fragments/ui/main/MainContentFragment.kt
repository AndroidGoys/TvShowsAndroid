package good.damn.tvlist.fragments.ui.main

import android.content.Context
import android.content.res.ColorStateList
import android.view.Gravity
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import good.damn.shaderblur.views.BlurShaderView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.adapters.FragmentAdapter
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.extensions.withAlpha
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.ui.main.pages.MediaListFragment
import good.damn.tvlist.fragments.ui.main.pages.TVProgramFragment
import good.damn.tvlist.views.navigation.NavigationView
import good.damn.tvlist.views.SearchView
import good.damn.tvlist.views.animatable.vectors.MediaVector
import good.damn.tvlist.views.animatable.vectors.PlayVector
import good.damn.tvlist.views.navigation.NavigationItem
import good.damn.tvlist.views.round.RoundedImageView

class MainContentFragment
: StackFragment() {

    companion object {
        private const val TAG = "MainContentFragment"
    }

    private var mBlurView: BlurShaderView? = null

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
        val navigationView = BottomNavigationView(
            context
        )


        val layoutTopBar = FrameLayout(
            context
        )
        mBlurView = BlurShaderView(
            context,
            viewPager,
            5,
            0.5f,
            0.43f
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

        viewPager.adapter = FragmentAdapter(
            arrayOf(
                TVProgramFragment(),
                MediaListFragment()
            ),
            childFragmentManager,
            lifecycle
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
        navigationView.setBackgroundColorId(
            R.color.background
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


        navigationView.itemRippleColor = ColorStateList.valueOf(
            App.color(
                R.color.navigationIcon
            )
        )
        navigationView.inflateMenu(
            R.menu.main_menu
        )

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

        layoutTopBarContent.gravity = Gravity
            .CENTER_HORIZONTAL

        // Bounds
        (measureUnit * 0.09903f).toInt().let {
            (measureUnit * 0.1715f).toInt().let { nativeHeight ->
                layoutTopBar.boundsFrame(
                    Gravity.TOP,
                    width = -1,
                    height = nativeHeight + getTopInset()
                )
                layoutTopBarContent.boundsFrame(
                    width = measureUnit,
                    height = layoutTopBar.heightParams()
                )

                layoutTopBarContent.setPadding(
                    0,
                    ((nativeHeight - it) * 0.5f).toInt(),
                    0,
                    0
                )
            }

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
            navigationView.boundsFrame(
                Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL,
                width = (measureUnit * 0.44202f).toInt(),
                height = bottomHeight,
                bottom = (measureUnit * 0.11352f)
            )


            navigationView.itemIconSize = (bottomHeight * 0.36363f).toInt()

            /*navigationView.radius = bottomHeight * 0.16363f
            navigationView.cardElevation = bottomHeight * 0.1f

            (bottomHeight * 0.36363f).toInt().let { vectorSize ->
                navigationView.items = arrayOf(
                    NavigationItem(
                        PlayVector(
                            (navigationView.widthParams() / 2 - vectorSize),
                            ((bottomHeight - vectorSize) * 0.5f).toInt(),
                            vectorSize,
                            vectorSize
                        )
                    ),
                    NavigationItem(
                        MediaVector(
                            (navigationView.widthParams() / 2 + vectorSize),
                            ((bottomHeight - vectorSize) * 0.5f).toInt(),
                            (vectorSize * 1.1f).toInt(),
                            vectorSize
                        )
                    )
                )
            }*/

        }

        layoutTopBarContent.apply {
            addView(imageViewProfile)
            addView(searchView)
            addView(imageViewLikes)
        }

        layoutTopBar.apply {
            addView(mBlurView)
            addView(layoutTopBarContent)
        }

        layout.apply {
            addView(viewPager)
            addView(layoutTopBar)
            addView(navigationView)
        }

        searchView.startAnimation()

        return layout
    }

    override fun onResume() {
        super.onResume()
        mBlurView?.startRenderLoop()
        mBlurView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mBlurView?.stopRenderLoop()
        mBlurView?.onPause()
    }
}