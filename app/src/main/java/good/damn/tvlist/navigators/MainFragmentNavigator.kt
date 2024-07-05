package good.damn.tvlist.navigators

import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import good.damn.tvlist.fragments.StackFragment
import java.util.LinkedList

class MainFragmentNavigator<FRAGMENT: Fragment>(
    private val mFragmentManager: FragmentManager,
    private val mContainer: FrameLayout
) {

    var size: Int = 0
        get() = mFragments.size

    var topFragment: FRAGMENT? = null
        get() = mFragments.last

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