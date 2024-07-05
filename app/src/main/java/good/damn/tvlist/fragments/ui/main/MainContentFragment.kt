package good.damn.tvlist.fragments.ui.main

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
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
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.fragments.ui.main.pages.MediaListFragment
import good.damn.tvlist.fragments.ui.main.pages.TVProgramFragment
import good.damn.tvlist.views.navigation.NavigationView
import good.damn.tvlist.views.SearchView
import good.damn.tvlist.views.animatable.vectors.MediaVector
import good.damn.tvlist.views.animatable.vectors.PlayVector
import good.damn.tvlist.views.navigation.NavigationItem
import good.damn.tvlist.views.navigation.interfaces.OnItemClickNavigationListener
import good.damn.tvlist.views.round.RoundedImageView

class MainContentFragment
: StackFragment(),
OnItemClickNavigationListener {

    companion object {
        private const val TAG = "MainContentFragment"
    }

    private var mBlurView: BlurShaderView? = null
    private lateinit var mViewPager: ViewPager2

    private val mFragments: Array<StackFragment> = arrayOf(
        TVProgramFragment(),
        MediaListFragment()
    )

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {
        val layout = FrameLayout(
            context
        )
        mViewPager = ViewPager2(
            context
        )
        val navigationView = NavigationView(
            context
        )


        val layoutTopBar = FrameLayout(
            context
        )
        mBlurView = BlurShaderView(
            context,
            mViewPager,
            7,
            0.5f,
            yMarginTop = 0.1f,
            yMarginBottom = 0.29f
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


        // Setup viewPager
        mViewPager.adapter = FragmentAdapter(
            mFragments,
            childFragmentManager,
            lifecycle
        )
        mViewPager.isUserInputEnabled = false



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

        // Navigation colors
        navigationView.selectedItemColor = App.color(
            R.color.navigationIcon
        )
        navigationView.itemColor = App.color(
            R.color.navigationIconBackground
        )


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
                    getTopInset() + ((nativeHeight - it) * 0.5f).toInt(),
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


            navigationView.radius = bottomHeight * 0.16363f
            navigationView.cardElevation = bottomHeight * 0.1f

            (bottomHeight * 0.36363f).toInt().let { vectorSize ->

                val playVector = PlayVector(
                    y = ((bottomHeight - vectorSize) * 0.5f).toInt(),
                    width = vectorSize,
                    height = vectorSize
                )

                playVector.color = App.color(
                    R.color.navigationIcon
                )

                navigationView.items = arrayOf(
                    NavigationItem(
                        playVector
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
            }

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
            addView(mViewPager)
            addView(layoutTopBar)
            addView(navigationView)
        }

        searchView.startAnimation()

        // Setup listeners
        navigationView.onItemClickListener = this
        navigationView.currentItem = 0


        imageViewProfile.setOnClickListener(
            this::onClickImageViewProfile
        )

        imageViewLikes.setOnClickListener(
            this::onClickImageViewLikes
        )

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

    override fun onClickNavigationItem(
        index: Int,
        navigationView: NavigationView
    ) {
        navigationView.currentItem = index
        mViewPager.currentItem = index
    }

    override fun onNetworkConnected() {
        mFragments[0].onNetworkConnected()
    }

    override fun onNetworkDisconnected() {
        mFragments[0].onNetworkDisconnected()
    }
}

private fun MainContentFragment.onClickImageViewLikes(
    v: View
) {

}

private fun MainContentFragment.onClickImageViewProfile(
    v: View
) {
    val location = IntArray(2)
    v.getLocationOnScreen(location)
    val right = location[0] + v.width
    val bottom = location[1] + v.height

    val dx = App.WIDTH - right
    val dy = App.HEIGHT - bottom

    pushFragment(
        ProfileFragment(),
        FragmentAnimation(
            duration = 350
        ) { f, fragment ->
            fragment.view?.apply {
                alpha = f
            }
            /*(fragment
                .view
                ?.layoutParams as? ViewGroup.MarginLayoutParams
                )?.apply {
                    val i = 1.0f - f
                    topMargin = (location[1] * f).toInt()
                    leftMargin = (location[0] * f).toInt()
                    rightMargin = (right + dx * i).toInt()
                    bottomMargin = (bottom + dy * i).toInt()
                    fragment.view?.layoutParams = this
                }*/
        }
    )
}