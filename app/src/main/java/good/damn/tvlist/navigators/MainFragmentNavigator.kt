package good.damn.tvlist.navigators

import android.util.Log
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import good.damn.tvlist.fragments.StackFragment
import java.util.LinkedList

class MainFragmentNavigator<FRAGMENT: Fragment>(
    private val mFragmentManager: FragmentManager,
    private val mContainer: FrameLayout
) {

    companion object {
        private const val TAG = "MainFragmentNavigator"
    }
    var size: Int = 0
        private set
        get() = mFragments.size

    var topFragment: FRAGMENT? = null
        private set
        get() = if (size == 0) null
        else mFragments.last

    var previousTopFragment: FRAGMENT? = null
        private set
        get() = if (
            mFragments.isEmpty()
        ) null else mFragments[
            when (mFragments.size) {
                1 -> 0
                else -> mFragments.size - 2
            }
        ]
    private val mFragments = LinkedList<FRAGMENT>()

    fun pushFragment(
        fragment: FRAGMENT
    ) {

        mFragments.add(
            fragment
        )

        mFragmentManager
            .beginTransaction()
            .add(
                mContainer.id,
                fragment
            ).commitAllowingStateLoss()
    }

    fun removeFragment(
        index: Int
    ) {
        removeFrag(
            mFragments.removeAt(
                index
            )
        )
    }

    fun popFragment() {
        removeFrag(
            mFragments.removeLast()
        )
    }

    fun getFragments(): LinkedList<FRAGMENT> {
        return mFragments
    }

    private fun removeFrag(
        fragment: FRAGMENT
    ) {
        mFragmentManager
            .beginTransaction()
            .remove(
                fragment
            ).commitAllowingStateLoss()
    }
}