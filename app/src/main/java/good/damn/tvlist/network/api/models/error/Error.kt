package good.damn.tvlist.network.api.models.error

import androidx.annotation.StringRes
import good.damn.tvlist.R
import good.damn.tvlist.extensions.extract
import org.json.JSONObject

data class Error(
    val code: Int,
    @StringRes val msgId: Int
) {
    companion object {
        private const val KEY_CODE = "errorCode"
        fun createFromJSON(
            json: JSONObject
        ): Error? {
            if (!json.has(KEY_CODE)) {
                return null
            }

            val errorCode = json.extract(
                KEY_CODE
            ) as? Int ?: 0

            return Error(
                errorCode,
                msgId(errorCode)
            )

        }

        @StringRes
        private fun msgId(
            error: Int
        ) = when (error) {
            1,4 -> R.string.invalid_credentials
            5 -> R.string.invalid_refresh_token
            9 -> R.string.user_exists
            else -> R.string.some_error_happens
        }

    }
}