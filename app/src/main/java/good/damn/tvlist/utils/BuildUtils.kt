package good.damn.tvlist.utils

import android.os.Build

class BuildUtils {
    companion object {
        fun isTiramisu33() = Build.VERSION
            .SDK_INT >= Build.VERSION_CODES.TIRAMISU

        fun isOreo26() = Build.VERSION
            .SDK_INT >= Build.VERSION_CODES.O
    }
}