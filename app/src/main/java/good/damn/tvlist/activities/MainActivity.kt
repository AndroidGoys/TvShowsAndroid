package good.damn.tvlist.activities

import android.content.Context
import android.content.IntentFilter
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorInt
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.animators.FragmentAnimator
import good.damn.tvlist.broadcast_receivers.network.NetworkReceiver
import good.damn.tvlist.broadcast_receivers.network.listeners.NetworkListener
import good.damn.tvlist.extensions.accessToken
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.extract
import good.damn.tvlist.extensions.generateId
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.normalWidth
import good.damn.tvlist.extensions.refreshToken
import good.damn.tvlist.extensions.toJSONObject
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.fragments.ui.splash.SplashFragment
import good.damn.tvlist.fragments.ui.main.MainContentFragment
import good.damn.tvlist.fragments.ui.main.tv_details.channel.TVChannelPageFragment
import good.damn.tvlist.models.AnimationConfig
import good.damn.tvlist.navigators.MainFragmentNavigator
import good.damn.tvlist.network.api.models.TVChannel2
import good.damn.tvlist.network.api.models.auth.TokenAuth
import good.damn.tvlist.network.api.services.AuthService
import good.damn.tvlist.network.api.services.TVChannel2Service
import good.damn.tvlist.utils.JWTUtils
import good.damn.tvlist.views.toasts.ToastImage
import kotlinx.coroutines.launch
import okhttp3.HttpUrl
import java.net.URL

class MainActivity
: AppCompatActivity(),
NetworkListener,
ActivityResultCallback<Boolean> {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var mNavigator: MainFragmentNavigator<StackFragment>
    private lateinit var mContainer: FrameLayout

    private var mPermissionLauncher: ActivityResultLauncher<String>? = null
    private var mContentBrowser: ActivityResultLauncher<String>? = null

    private var mWindowController: WindowInsetsControllerCompat? = null

    private val mAnimator = FragmentAnimator()

    private val mNetworkReceiver = NetworkReceiver()

    private var mPermissionFragment: StackFragment? = null
    private var mBrowserFragment: StackFragment? = null

    private var mTopInset = 0

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        registerReceiver(
            mNetworkReceiver,
            IntentFilter(
                ConnectivityManager
                    .CONNECTIVITY_ACTION
            )
        )

        getPreferences(
            Context.MODE_PRIVATE
        ).apply {

            val refreshToken = refreshToken()
                ?: return@apply

            val accessToken = accessToken()
                ?: return@apply

            App.TOKEN_AUTH = TokenAuth(
                accessToken,
                refreshToken
            )

        }

        mPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
            this
        )

        mContentBrowser = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            mBrowserFragment?.onGetContentUri(
                it
            )
            mBrowserFragment = null
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        mNetworkReceiver.networkListener = this

        val context = this

        mAnimator.duration = 350
        mAnimator.interpolator = AccelerateDecelerateInterpolator()
        mAnimator.setFloatValues(
            0.0f,1.0f
        )

        mContainer = FrameLayout(
            context
        )
        mNavigator = MainFragmentNavigator(
            supportFragmentManager,
            mContainer
        )

        ViewCompat.setOnApplyWindowInsetsListener(
            window.decorView
        ) { v, insets ->
            // WTF is deprecated? (systemWindowInsetTop and stableInsetTop)
            if (mTopInset == 0) {
                mTopInset = insets.stableInsetTop
            }

            Log.d(TAG, "onCreate: INSETS: $mTopInset")

            ViewCompat.setOnApplyWindowInsetsListener(
                v, null
            )

            WindowInsetsCompat.CONSUMED
        }

        mContainer.generateId()
        mContainer.post {
            App.WIDTH = mContainer.width
            App.HEIGHT = mContainer.height

            val splash = SplashFragment()

            splash.onAnimationEnd = this@MainActivity::onSplashEnd

            pushFragment(
                splash,
                FragmentAnimation { factor, fragment ->
                    fragment.view?.alpha = factor
                }
            )

        }

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(
                true
            ) {
                override fun handleOnBackPressed() {
                    if (mNavigator.size <= 1) {
                        finish()
                        return
                    }
                    popFragment(
                        FragmentAnimation { f, fragment ->
                            fragment.view?.scaleX = 1.0f - f
                        }
                    )
                }
            }
        )


        setContentView(
            mContainer
        )
    }

    override fun onWindowFocusChanged(
        hasFocus: Boolean
    ) {
        super.onWindowFocusChanged(hasFocus)

        mWindowController = WindowInsetsControllerCompat(
            window,
            window.decorView
        )
    }

    fun getTopInset() = mTopInset

    fun pushFragment(
        fragment: StackFragment,
        withAnimation: FragmentAnimation? = null
    ) {
        mNavigator.topFragment?.onFocusChanged(
            false
        )

        mNavigator.pushFragment(
            fragment
        )

        if (withAnimation == null) {
            return
        }

        mAnimator.setDuration(
            withAnimation.duration.toLong()
        )

        mAnimator.startTransition(
            inAnimation = withAnimation,
            inFragment = fragment
        )
    }

    fun popFragment(
        withAnimation: FragmentAnimation? = null
    ) {
        mNavigator
            .previousTopFragment
            ?.onFocusChanged(
                true
            )
        if (withAnimation == null) {
            mNavigator.popFragment()
            return
        }
        mAnimator.onAnimationEnd = {
            mNavigator.popFragment()
        }
        mAnimator.startTransition(
            outAnimation = withAnimation,
            outFragment = mNavigator.topFragment
        )
    }

    fun replaceFragment(
        on: StackFragment,
        baseAnimation: FragmentAnimation? = null,
        onAnimation: FragmentAnimation? = null
    ) {
        if (baseAnimation == null || onAnimation == null) {
            popFragment()
            pushFragment(
                on
            )
            return
        }
        val topFragment = mNavigator.topFragment
        pushFragment(
            on
        )
        mAnimator.onAnimationEnd = {
            mNavigator.removeFragment(
                mNavigator.size - 2
            )
        }
        mAnimator.startTransition(
            baseAnimation,
            topFragment,
            onAnimation,
            on
        )
    }


    fun isFragmentFocused(
        targetFragment: StackFragment
    ) = mNavigator
        .topFragment
        ?.equals(
            targetFragment
        ) ?: false


    fun requestContentBrowser(
        mimeType: String,
        fragment: StackFragment
    ) {
        mBrowserFragment = fragment
        mContentBrowser?.launch(
            mimeType
        )
    }

    fun requestPermission(
        permission: String,
        fragment: StackFragment
    ) {
        mPermissionLauncher?.apply {
            mPermissionFragment = fragment
            launch(permission)
        }
    }

    fun toast(
        durationShow: Long,
        textSizeFactor: Float,
        drawable: Drawable? = null,
        text: String?,
        animation: AnimationConfig
    ) {
        Log.d(TAG, "toast: ${mContainer.childCount} ${mNavigator.size}")
        if (mContainer.childCount > mNavigator.size) {
            return
        }

        ToastImage(
            this
        ).apply {
            textView.text = text
            imageView.setImageDrawable(
                drawable
            )

            this.textSizeFactor = textSizeFactor

            this.durationShow = durationShow

            cardElevation = 0.0f

            val bottom = App.HEIGHT * 0.1f

            boundsFrame(
                Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM,
                width = (App.WIDTH * 337.normalWidth()).toInt(),
                height = (App.WIDTH * 51.normalWidth()).toInt(),
                bottom = bottom
            )

            radius = 0.19f * heightParams()

            y = App.HEIGHT - bottom

            mContainer.addView(
                this
            )

            show(animation)
        }
    }

    override fun onNetworkConnected() {
        App.NETWORK_AVAILABLE = true
        mNavigator.getFragments().forEach {
            it.onNetworkConnected()
        }
    }

    override fun onNetworkDisconnected() {
        App.NETWORK_AVAILABLE = false
        toast(
            2500L,
            0.28f,
            App.drawable(
                R.drawable.ic_info_white
            ),
            getString(
                R.string.no_internet_connection
            ),
            AnimationConfig(
                500,
                OvershootInterpolator()
            )
        )
    }

    override fun onActivityResult(
        isGrantedPermission: Boolean
    ) {
        mPermissionFragment?.onGrantPermission(
            isGrantedPermission
        )
        mPermissionFragment = null
    }

    @MainThread
    private fun startMainFragment() {
        val intent = intent
        if (intent == null) {
            defaultInitFragment()
            return
        }

        // Deep link manager
        val data = intent.data
        Log.d(TAG, "onCreate: DEEP_LINK: $data")

        if (data == null) {
            defaultInitFragment()
            return
        }

        if (data.host != "app.show") {
            defaultInitFragment()
            return
        }

        val id = try {
            data.getQueryParameter(
                "id"
            )?.toInt() ?: -1
        } catch (e: NumberFormatException) {
            -1
        }

        if (id == -1) {
            defaultInitFragment()
            return
        }

        App.IO.launch {
            val channel = TVChannel2Service().getChannelDetails(
                id
            )

            if (channel == null) {
                App.ui {
                    defaultInitFragment()
                }
                return@launch
            }

            App.ui {
                initFragment(
                    TVChannelPageFragment.newInstance(
                        channel
                    )
                )
            }

        }

    }

    @MainThread
    private fun defaultInitFragment() {
        initFragment(
            MainContentFragment()
        )
    }

    @MainThread
    private fun initFragment(
        fragment: StackFragment
    ) {
        replaceFragment(
            fragment,
            baseAnimation = FragmentAnimation { f, fragment ->
                fragment.view?.y = App.HEIGHT * f
            },
            onAnimation = FragmentAnimation { f, fragment ->
                fragment.view?.y = App.HEIGHT * (f-1.0f)
            }
        )
    }

    private fun onSplashEnd() {
        val token = App.TOKEN_AUTH
        if (token == null) {
            startMainFragment()
            return
        }

        val prefs = getPreferences(
            Context.MODE_PRIVATE
        )

        val expiration = try {
            JWTUtils.decode(
                token.accessToken
            ).extract(
                1
            )?.toJSONObject()?.extract(
                "exp"
            ) as? Int ?: 0
        } catch (e: Exception) {
            0
        }

        Log.d(TAG, "onSplashEnd: CHECK_TIME: ${App.CURRENT_TIME_SECONDS} $expiration")

        if (App.CURRENT_TIME_SECONDS > expiration) {
            startMainFragment()
            return
        }

        App.IO.launch {
            val result = AuthService().refreshAccess(
                token.refreshToken
            )

            if (result.errorStringId != -1) {
                App.ui {
                    toast(
                        2000L,
                        0.2f,
                        text = getString(result.errorStringId),
                        animation = AnimationConfig(
                            350
                        )
                    )
                }
            }

            val newToken = result.result
            if (newToken != null) {
                App.TOKEN_AUTH = newToken
                prefs.edit().apply {
                    accessToken(newToken.accessToken)
                    refreshToken(newToken.refreshToken)
                    commit()
                }
            }

            App.ui {
                startMainFragment()
            }

        }
    }

}