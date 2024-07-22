package good.damn.tvlist.fragments

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.activities.MainActivity
import good.damn.tvlist.extensions.mainActivity
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.models.AnimationConfig
import good.damn.tvlist.views.text_fields.TextFieldRound

abstract class StackFragment
: NetworkFragment() {

    private var mInputManager: InputMethodManager? = null

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )

        mInputManager = context?.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as? InputMethodManager
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
    ) {
        enableInteraction(
            isFragmentFocused
        )
    }

    open fun onGrantPermission(
        isGranted: Boolean
    ) {}

    open fun onGetContentUri(
        uri: Uri?
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

    fun isFragmentFocused() = mainActivity()
        .isFragmentFocused(
            this
        )

    fun requestContentBrowser(
        mimeType: String
    ) = mainActivity().requestContentBrowser(
        mimeType,
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
        durationShow: Long,
        textSizeFactor: Float,
        text: String,
        drawable: Drawable? = null,
        animation: AnimationConfig
    ) = mainActivity().toast(
        durationShow,
        textSizeFactor,
        drawable,
        text,
        animation
    )

    fun toast(
        @StringRes id: Int
    ) {
        mainActivity().toast(
            durationShow = 3000L,
            textSizeFactor = 0.28f,
            App.drawable(
                R.drawable.ic_info_white
            ),
            getString(id),
            AnimationConfig(
                300,
                AccelerateDecelerateInterpolator()
            )
        )
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

    fun focusOnTextField(
        it: TextFieldRound
    ) {
        it.requestFocus()
        mInputManager?.showSoftInput(
            it,
            InputMethodManager.SHOW_IMPLICIT
        )
    }


    protected abstract fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View
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
