package good.damn.tvlist.extensions

import android.util.Log
import android.util.Patterns
import good.damn.tvlist.R
import good.damn.tvlist.models.Result
import org.json.JSONObject

fun String.isEmail() = Patterns
    .EMAIL_ADDRESS
    .matcher(this)
    .matches()

fun String.isStrongPassword(): Result<Void> {

    if (this.length < 8) {
        return Result(
            errorStringId = R.string.password_less_than_8
        )
    }

    if (!this.contains("[0-9]".toRegex())) {
        return Result(
            errorStringId = R.string.password_has_not_numbers
        )
    }

    if (!this.contains("[a-zA-Z]")) {
        return Result(
            errorStringId = R.string.password_has_not_latin
        )
    }

    if (!this.contains("_".toRegex())) {
        return Result(
            errorStringId = R.string.password_has_not_lowerscape
        )
    }

    return Result()
}

fun String.toAuthToken() = "Bearer $this"

fun String.toJSONObject() = try {
    JSONObject(
        this
    )
} catch (e: Exception) {
    Log.d("String", "toJSONObject: ERROR_PARSE: ${e.message}")
    null
}