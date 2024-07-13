package good.damn.tvlist.activities

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.animators.FragmentAnimator
import good.damn.tvlist.broadcast_receivers.network.NetworkReceiver
import good.damn.tvlist.broadcast_receivers.network.listeners.NetworkListener
import good.damn.tvlist.extensions.generateId
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.fragments.ui.splash.SplashFragment
import good.damn.tvlist.fragments.ui.main.MainContentFragment
import good.damn.tvlist.navigators.MainFragmentNavigator

class MainActivity
: AppCompatActivity(),
NetworkListener,
ActivityResultCallback<Boolean> {

    companion object {
        private const val TAG = "MainActivity"
    }

    @ColorInt
    var navigationBarColor: Int = 0
        set(v) {
            window.navigationBarColor = v
            field = v
        }

    @ColorInt
    var statusBarColor: Int = 0
        set(v) {
            window.statusBarColor = v
            field = v
        }

    private lateinit var mNavigator: MainFragmentNavigator<StackFragment>
    private lateinit var mContainer: FrameLayout

    private var mPermissionLauncher: ActivityResultLauncher<String>? = null

    private var mWindowController: WindowInsetsControllerCompat? = null

    private val mAnimator = FragmentAnimator()

    private val mNetworkReceiver = NetworkReceiver()

    private var mPermissionFragment: StackFragment? = null

    private var mTopInset = 0

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        mPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
            this
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

            splash.onAnimationEnd = {
                replaceFragment(
                    MainContentFragment(),
                    baseAnimation = FragmentAnimation { f, fragment ->
                        fragment.view?.y = App.HEIGHT * f
                    },
                    onAnimation = FragmentAnimation { f, fragment ->
                        fragment.view?.y = App.HEIGHT * (f-1.0f)
                    }
                )

                registerReceiver(
                    mNetworkReceiver,
                    IntentFilter(
                        ConnectivityManager
                            .CONNECTIVITY_ACTION
                    )
                )
            }

            pushFragment(
                splash,
                FragmentAnimation { factor, fragment ->
                    fragment.view?.alpha = factor
                }
            )

        }

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

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    // WTF is deprecated? (systemWindowInsetTop)
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

    fun showStatusBar() {
        mWindowController?.show(
            WindowInsetsCompat.Type.statusBars()
        )
    }

    fun hideStatusBar() {
        mWindowController?.show(
            WindowInsetsCompat.Type.statusBars()
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

    override fun onNetworkConnected() {
        App.NETWORK_AVAILABLE = true
        mNavigator.getFragments().forEach {
            it.onNetworkConnected()
        }
    }

    override fun onNetworkDisconnected() {
        App.NETWORK_AVAILABLE = false
        Toast.makeText(
            this,
            R.string.no_internet_connection,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onActivityResult(
        isGrantedPermission: Boolean
    ) {
        mPermissionFragment?.onGrantPermission(
            isGrantedPermission
        )
        mPermissionFragment = null
    }

}