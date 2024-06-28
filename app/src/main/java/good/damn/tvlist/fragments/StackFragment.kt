package good.damn.tvlist.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import good.damn.tvlist.App
import good.damn.tvlist.activities.MainActivity
import good.damn.tvlist.fragments.animation.FragmentAnimation

abstract class StackFragment
: Fragment() {

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

        return v
    }

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

    abstract fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View
}

private fun StackFragment.mainActivity(): MainActivity {
    return activity as MainActivity
}