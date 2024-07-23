package good.damn.tvlist.utils

import android.util.Base64
import android.util.Log
import good.damn.tvlist.extensions.extract
import java.nio.charset.StandardCharsets

class JWTUtils {
    companion object {
        private const val TAG = "JWTUtils"
        fun decode(
            inp: String
        ): List<String> {
            val sp = inp.split(
                "\\.".toRegex()
            )
            return Array(sp.size) {
                getJSON(sp[it])
            }.toList()
        }

        private fun getJSON(
            str: String
        ): String {
            val decoded = Base64.decode(
                str,
                Base64.URL_SAFE
            )

            return String(
                decoded,
                StandardCharsets.UTF_8
            )
        }
    }
}