package good.damn.tvlist.fragments.ui.main

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.DatePicker
import android.widget.FrameLayout
import android.widget.TimePicker
import androidx.viewpager2.widget.ViewPager2
import good.damn.shaderblur.views.BlurShaderView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.adapters.FragmentAdapter
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.getTimeInSeconds
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.rgba
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.extensions.withAlpha
import good.damn.tvlist.fragments.SearchFragment
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.fragments.ui.auth.OnAuthListener
import good.damn.tvlist.fragments.ui.main.interfaces.OnUpdateProfileImageListener
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
import java.util.Calendar

class MainContentFragment
: StackFragment(),
OnItemClickNavigationListener,
OnUpdateProfileImageListener,
OnDateSetListener,
OnTimeSetListener {

    companion object {
        private const val TAG = "MainContentFragment"
    }

    private var mBlurView: BlurShaderView? = null
    private var mImageViewProfile: RoundedImageView? = null
    private var mSearchView: SearchView? = null
    private var mViewPager: ViewPager2? = null

    private var mDatePickerDialog: DatePickerDialog? = null
    private var mTimePickerDialog: TimePickerDialog? = null

    private val mFragmentReleases = TVChannelReleaseFragment()

    private val mFragments: Array<StackFragment> = arrayOf(
        mFragmentReleases,
        MediaListFragment()
    )

    private val mUserService = UserService()

    private val mChannelCalendar = Calendar.getInstance()

    override fun onAnimationEnd() {
        super.onAnimationEnd()
        mFragmentReleases.onAnimationEnd()
        mSearchView?.startAnimation()
    }

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {

        mChannelCalendar.apply {
            mDatePickerDialog = DatePickerDialog(
                context,
                this@MainContentFragment,
                get(Calendar.YEAR),
                get(Calendar.MONTH),
                get(Calendar.DAY_OF_MONTH)
            )

            mTimePickerDialog = TimePickerDialog(
                context,
                this@MainContentFragment,
                get(Calendar.HOUR_OF_DAY),
                get(Calendar.MINUTE),
                true
            )

        }

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

        val imageViewDate = RoundedImageView(
            context
        ).apply {
            drawable = App.drawable(
                R.drawable.ic_calendar
            )
            imageScaleY = 0.75f
            imageScaleX = 0.75f

            setOnClickListener(
                this@MainContentFragment::onClickDatePicker
            )

        }

        // Bounds
        (measureUnit * 0.09903f).toInt().let { iconHeight ->
            val profileSearchHeight = measureUnit * 0.1715f
            val topInset = getTopInset()
            val topInsetContent = topInset + (
                profileSearchHeight - iconHeight
            ) * 0.5f

            val marginBetweenContent = measureUnit * 0.04348f
            val searchWidth = measureUnit * 0.54251f

            val marginHorizontal = (measureUnit -
                marginBetweenContent * 2 -
                iconHeight * 3 -
                searchWidth) / 2

            val searchViewBottomPosX = marginBetweenContent +
                iconHeight +
                marginHorizontal +
                searchWidth

            mImageViewProfile?.boundsFrame(
                width = iconHeight,
                height = iconHeight,
                top = topInsetContent,
                left = marginHorizontal
            )

            mSearchView?.boundsFrame(
                width = searchWidth.toInt(),
                height = iconHeight,
                left = searchViewBottomPosX - searchWidth,
                top = topInsetContent
            )

            (marginBetweenContent * 0.5f).let { halfInterval ->
                (searchViewBottomPosX + halfInterval).let {
                    imageViewDate.boundsFrame(
                        width = iconHeight,
                        height = iconHeight,
                        left = it,
                        top = topInsetContent
                    )

                    imageViewLikes.boundsFrame(
                        width = iconHeight,
                        height = iconHeight,
                        left = it + halfInterval + iconHeight,
                        top = topInsetContent
                    )
                }
            }



            layoutTopBar.boundsFrame(
                Gravity.TOP,
                width = -1,
                height = profileSearchHeight.toInt()
                    + topInset
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
            addView(imageViewDate)
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
        mFragmentReleases.onNetworkConnected()
    }

    override fun onNetworkDisconnected() {
        mFragmentReleases.onNetworkDisconnected()
    }

    override fun onUpdateProfileImage(
        b: Bitmap?,
        urlProfile: String
    ) {
        mImageViewProfile?.apply {
            val h = heightParams()

            bitmap = if (b == null)
                null
            else NetworkBitmap.contextCache(
                b,
                urlProfile,
                App.CACHE_DIR,
                UserService.DIR_AVATAR,
                h,
                h
            )

            invalidate()
        }
    }

    override fun onTimeSet(
        view: TimePicker?,
        hourOfDay: Int,
        minute: Int
    ) {
        Log.d(TAG, "onTimeSet: SAVED_DATE: ${mChannelCalendar.getTimeInSeconds()}")
        mChannelCalendar.apply {
            set(
                get(Calendar.YEAR),
                get(Calendar.MONTH),
                get(Calendar.DAY_OF_MONTH),
                hourOfDay,
                minute
            )

            Log.d(TAG, "onTimeSet: NEW_TIME: ${mChannelCalendar.getTimeInSeconds()}")

            mFragmentReleases.onCalendarSet(
                this
            )
        }
    }

    override fun onDateSet(
        view: DatePicker?,
        year: Int,
        month: Int,
        dayOfMonth: Int
    ) {
        Log.d(TAG, "onDateSet: PREV_DATE: ${mChannelCalendar.getTimeInSeconds()}")
        mChannelCalendar.apply {
            set(
                year,
                month,
                dayOfMonth
            )

            mTimePickerDialog?.show()
        }
    }

    private fun onClickDatePicker(
        v: View
    ) {
        mDatePickerDialog?.apply {
            mChannelCalendar.apply {
                updateDate(
                    get(Calendar.YEAR),
                    get(Calendar.MONTH),
                    get(Calendar.DAY_OF_MONTH)
                )
            }
            show()
        }
    }

    private fun onClickImageViewProfile(
        v: View
    ) {
        pushFragment(
            ProfileFragment().apply {
                onUpdateProfileImageListener = this@MainContentFragment
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