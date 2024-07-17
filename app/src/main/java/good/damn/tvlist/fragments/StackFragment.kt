package good.damn.tvlist.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import good.damn.tvlist.App
import good.damn.tvlist.activities.MainActivity
import good.damn.tvlist.extensions.mainActivity
import good.damn.tvlist.fragments.animation.FragmentAnimation

abstract class StackFragment
: NetworkFragment() {

    @ColorInt
    var navigationBarColor: Int = 0
        set(v) {
            field = v
            mainActivity().navigationBarColor = v
        }

    @ColorInt
    var statusBarColor: Int = 0
        set(v) {
            field = v
            mainActivity().statusBarColor = v
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = context
            ?: return null

        val v = onCreateView(
            context,
            App.WIDTH
        )

        v.isClickable = true

        return v
    }

    open fun onFocusChanged(
        isFragmentFocused: Boolean
    ) {}

    open fun onGrantPermission(
        isGranted: Boolean
    ) {}

    fun pushFragment(
        fragment: StackFragment,
        withAnimation: FragmentAnimation? = null
    ) {
        mainActivity().pushFragment(
            fragment,
            withAnimation
        )
    }

    fun replaceFragment(
        fragment: StackFragment,
        outAnimation: FragmentAnimation? = null,
        inAnimation: FragmentAnimation? = null
    ) {
        mainActivity().replaceFragment(
            fragment,
            outAnimation,
            inAnimation
        )
    }

    fun popFragment(
        withAnimation: FragmentAnimation? = null
    ) {
        mainActivity().popFragment(
            withAnimation
        )
    }

    fun getTopInset() = mainActivity()
        .getTopInset()

    fun showStatusBar() = mainActivity()
        .showStatusBar()

    fun hideStatusBar() = mainActivity()
        .hideStatusBar()

    fun isFragmentFocused() = mainActivity()
        .isFragmentFocused(
            this
        )

    fun requestPermission(
        permission: String
    ) = mainActivity().requestPermission(
        permission,
        this
    )

    fun sharedStorage() = mainActivity()
        .getPreferences(
            Context.MODE_PRIVATE
        )

    fun toast(
        @StringRes id: Int
    ) {
        Toast.makeText(
            context,
            id,
            Toast.LENGTH_SHORT
        ).show()
    }

    fun toast(
        msg: String
    ) {
        Toast.makeText(
            context,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    fun enableInteraction(
        isIt: Boolean
    ) {
        val group = (view as? ViewGroup)
            ?: return
        isEnabledViewGroup(
            group,
            isIt
        )
    }

    private fun isEnabledViewGroup(
        group: ViewGroup,
        isIt: Boolean
    ) {
        (group as? ViewGroup)?.children?.forEach {
            if (it is ViewGroup) {
                isEnabledViewGroup(
                    it,
                    isIt
                )
                return@forEach
            }

            it.isEnabled = isIt
        }
    }

    protected abstract fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View
}