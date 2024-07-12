package good.damn.tvlist.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import java.security.Permission
import java.security.Permissions

class PermissionUtils {
    companion object {
        fun checkNotifications(
            context: Context
        ) = if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
        ) ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
        else true

    }
}