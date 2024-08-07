package good.damn.tvlist.fragments.ui.main.interfaces

import android.graphics.Bitmap

interface OnUpdateProfileImageListener {
    fun onUpdateProfileImage(
        b: Bitmap?,
        urlProfile: String
    )
}