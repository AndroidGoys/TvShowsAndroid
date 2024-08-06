package good.damn.tvlist.fragments.ui.main

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import good.damn.shaderblur.views.BlurShaderView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.adapters.FragmentAdapter
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.leftParams
import good.damn.tvlist.extensions.rgba
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.extensions.withAlpha
import good.damn.tvlist.fragments.SearchFragment
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.fragments.ui.auth.OnAuthListener
import good.damn.tvlist.fragments.ui.main.pages.MediaListFragment
import good.damn.tvlist.fragments.ui.main.pages.TVChannelReleaseFragment
import good.damn.tvlist.network.api.services.UserService
import good.damn.tvlist.network.bitmap.NetworkBitmap
import good.damn.tvlist.views.navigation.NavigationView
import good.damn.tvlist.views.SearchView
import good.damn.tvlist.views.animatable.vectors.MediaVector
import good.damn.tvlist.views.animatable.vectors.PlayVector
import good.damn.tvlist.views.navigation.NavigationItem
import good.damn.tvlist.views.navigation.interfaces.OnItemClickNavigationListener
import good.damn.tvlist.views.round.RoundedImageView
import kotlinx.coroutines.launch

class MainContentFragment
: StackFragment(),
OnItemClickNavigationListener,
OnAuthListener {

    companion object {
        private const val TAG = "MainContentFragment"
    }

    private var mBlurView: BlurShaderView? = null
    private var mImageViewProfile: RoundedImageView? = null
    private var mSearchView: SearchView? = null
    private var mViewPager: ViewPager2? = null

    private val mFragments: Array<StackFragment> = arrayOf(
        TVChannelReleaseFragment(),
        MediaListFragment()
    )

    private val mUserService = UserService()

    override fun onAnimationEnd() {
        super.onAnimationEnd()
        mFragments[0].onAnimationEnd()

        mSearchView?.startAnimation()
        setUserAvatar()
    }

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {


        val layout = FrameLayout(
            context
        ).apply {
            setBackgroundColorId(
                R.color.background
            )
        }


        mViewPager = ViewPager2(
            context
        ).apply {
            adapter = FragmentAdapter(
                mFragments,
                childFragmentManager,
                lifecycle
            )
            isUserInputEnabled = false

            mBlurView = BlurShaderView(
                context,
                this,
                7,
                0.35f,
                App.color(
                    R.color.background
                ).withAlpha(
                    0.5f
                ).rgba()
            ).apply {
                isClickable = true
            }
        }


        val navigationView = NavigationView(
            context
        ).apply {
            selectedItemColor = App.color(
                R.color.navigationIcon
            )
            itemColor = App.color(
                R.color.navigationIconBackground
            )

            setBackgroundColorId(
                R.color.background
            )
        }


        val layoutTopBar = FrameLayout(
            context
        ).apply {
            setBackgroundColor(0)
        }

        mImageViewProfile = RoundedImageView(
            context
        ).apply {
            setStrokeColorId(
                R.color.lime
            )
            drawable = App.drawable(
                R.drawable.ic
            )
            setOnClickListener(
                this@MainContentFragment
                ::onClickImageViewProfile
            )
        }



        mSearchView = SearchView(
            context
        ).apply {
            setBackgroundColorId(
                R.color.searchViewBack
            )
            App.color(
                R.color.searchText
            ).withAlpha(0.39f).let {
                textColorWord = it
                textColorExample = it
            }
            typefaceExample = App.font(
                R.font.open_sans_regular,
                context
            )

            typefaceWord = App.font(
                R.font.open_sans_bold,
                context
            )

            setOnClickListener(
                this@MainContentFragment::onClickSearch
            )

            // Text
            textExample = getString(
                R.string.for_example
            )

            textWords = arrayOf(
                "стс",
                "start",
                "вверх",
                "чебурашка"
            )

            // Text size
            textSizeWord = 0.29268f
            textSizeExample = 0.29268f

            // Icon Size
            iconSize = 0.4878f
            iconPaddingRight = 0.041353f

            // Search view animation config
            animationDuration = 750
            animationInterval = 4000
            animationInterpolator = LinearInterpolator()

            App.drawable(
                R.drawable.ic_lens
            )?.let {
                it.alpha = (255 * 0.48f).toInt()
                drawable = it
            }
        }


        val imageViewLikes = RoundedImageView(
            context
        ).apply {
            strokeColor = 0x3318191F
            drawable = App.drawable(
                R.drawable.ic_heart
            )
            imageScaleX = 0.5f
            imageScaleY = 0.5f
        }

        // Bounds
        (measureUnit * 0.09903f).toInt().let { iconHeight ->
            val profileSearchHeight = measureUnit * 0.1715f
            val dateHeight = measureUnit * 0.0315f
            val topInset = getTopInset()
            val topInsetContent = topInset + (
                profileSearchHeight - iconHeight
            ) * 0.5f

            val marginBetweenContent = measureUnit * 0.04348f
            val searchWidth = measureUnit * 0.64251f

            val marginHorizontal = (measureUnit -
                marginBetweenContent * 2 -
                iconHeight * 2 -
                searchWidth) / 2

            val searchViewBottomPosX = marginBetweenContent +
                iconHeight +
                marginHorizontal +
                searchWidth

            mSearchView?.boundsLinear(
                width = searchWidth.toInt(),
                height = iconHeight,
                left = searchViewBottomPosX - searchWidth,
                top = topInsetContent
            )

            imageViewLikes.boundsLinear(
                width = iconHeight,
                height = iconHeight,
                left = searchViewBottomPosX + marginBetweenContent,
                top = topInsetContent
            )

            layoutTopBar.boundsFrame(
                Gravity.TOP,
                width = -1,
                height = profileSearchHeight.toInt()
                    + dateHeight.toInt()
                    + topInset
            )

            mImageViewProfile?.boundsLinear(
                width = iconHeight,
                height = iconHeight,
                top = topInsetContent,
                left = marginHorizontal
            )

            (iconHeight * 0.04878f).let { strokeWidth ->
                mImageViewProfile?.strokeWidth = strokeWidth
                imageViewLikes.strokeWidth = strokeWidth
            }

            mImageViewProfile?.cornerRadius = iconHeight * 0.5f
            imageViewLikes.cornerRadius = iconHeight * 0.5f

            mSearchView?.apply {
                cornerRadius = iconHeight * 0.2317f
                setPadding(
                    (widthParams() * 0.04887f).toInt(),
                    0,
                    0,
                    0
                )
            }
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

        layoutTopBar.apply {
            addView(mBlurView)
            addView(mImageViewProfile)
            addView(mSearchView)
            addView(imageViewLikes)
        }

        layout.apply {
            addView(mViewPager)
            addView(layoutTopBar)
            addView(navigationView)
        }

        // Setup listeners
        navigationView.onItemClickListener = this
        navigationView.currentItem = 0


        imageViewLikes.setOnClickListener(
            this::onClickImageViewLikes
        )

        return layout
    }

    override fun onFocusChanged(
        isFragmentFocused: Boolean
    ) {
        super.onFocusChanged(isFragmentFocused)
        Log.d(TAG, "onFocusChanged: $isFragmentFocused")
        mBlurView?.apply {
            if (isFragmentFocused) {
                startRenderLoop()
                onResume()
                mSearchView?.startAnimation()
                return@apply
            }

            stopRenderLoop()
            onPause()
            mSearchView?.pauseAnimation()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ${isFragmentFocused()}")
        if (!isFragmentFocused()) {
            return
        }
        mBlurView?.apply {
            startRenderLoop()
            onResume()
        }
        mSearchView?.startAnimation()
    }

    override fun onPause() {
        Log.d(TAG, "onPause: ${isFragmentFocused()}")
        super.onPause()
        if (!isFragmentFocused()) {
            return
        }
        mBlurView?.apply {
            stopRenderLoop()
            onPause()
        }
        mSearchView?.pauseAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
        mBlurView?.clean()
    }
    
    override fun onClickNavigationItem(
        index: Int,
        navigationView: NavigationView
    ) {
        navigationView.currentItem = index
        mViewPager?.currentItem = index
    }

    override fun onNetworkConnected() {
        mFragments[0].onNetworkConnected()
    }

    override fun onNetworkDisconnected() {
        mFragments[0].onNetworkDisconnected()
    }


    override fun onAuthSuccess() {
        Log.d(TAG, "onAuthSuccess: ")
        setUserAvatar()
    }

    private fun setUserAvatar() {
        Log.d(TAG, "setUserAvatar: $mImageViewProfile")
        val imageViewProfile = mImageViewProfile
            ?: return

        App.IO.launch {
            Log.d(TAG, "setUserAvatar: ACCESS_TOKEN: ${App.TOKEN_AUTH?.accessToken}")
            mUserService.updateAccessToken(
                App.TOKEN_AUTH?.accessToken ?: ""
            )
            val profile = mUserService.getProfile(
                fromCache = true
            )
            Log.d(TAG, "setUserAvatar: PROFILE: ${profile?.avatarUrl}")

            if (profile == null) {
                return@launch
            }

            App.ui {
                val s = imageViewProfile.heightParams()
                profile.avatarUrl ?: return@ui
                NetworkBitmap.loadFromNetwork(
                    profile.avatarUrl,
                    App.CACHE_DIR,
                    UserService.DIR_AVATAR,
                    s,
                    s
                ) {
                    imageViewProfile.bitmap = it
                    imageViewProfile.invalidate()
                }

            }
        }
    }

    private fun onClickImageViewProfile(
        v: View
    ) {
        pushFragment(
            ProfileFragment().apply {
                onAuthListener = this@MainContentFragment
            },
            FragmentAnimation(
                duration = 350
            ) { f, fragment ->
                fragment.view?.apply {
                    alpha = f
                }
            }
        )
    }

}

private fun MainContentFragment.onClickSearch(
    view: View
) {
    pushFragment(
        SearchFragment(),
        FragmentAnimation { f, fragment ->
            fragment.view?.y = App.HEIGHT * (1.0f-f)
        }
    )
}

private fun MainContentFragment.onClickImageViewLikes(
    v: View
) {
    pushFragment(
        FavouritesFragment(),
        FragmentAnimation { f, fragment ->
            fragment.view?.apply {
                x = App.WIDTH * (1.0f-f)
            }
        }
    )
}