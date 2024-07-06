package good.damn.tvlist.extensions

import androidx.fragment.app.Fragment
import good.damn.tvlist.activities.MainActivity

fun Fragment.mainActivity(): MainActivity {
    return activity as MainActivity
}