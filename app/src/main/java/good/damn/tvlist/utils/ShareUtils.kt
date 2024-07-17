package good.damn.tvlist.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import good.damn.tvlist.R
import good.damn.tvlist.extensions.getDayString
import good.damn.tvlist.extensions.getMonthString
import java.util.Calendar

class ShareUtils {
    companion object {
        fun shareWithImage(
            context: Context,
            text: String,
            imageUri: Uri?
        ) {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "image/*"
                putExtra(Intent.EXTRA_TEXT, text)
                putExtra(Intent.EXTRA_STREAM, imageUri)
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }

            context.startActivity(
                Intent.createChooser(
                    intent,
                    null
                )
            )

        }
    }
}