package good.damn.tvlist.utils

import android.os.Build

class BuildUtils {
    companion object {
        fun isTiramisu() = Build.VERSION
            .SDK_INT >= Build.VERSION_CODES
                .TIRAMISU
    }
}