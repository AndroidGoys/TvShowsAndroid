package good.damn.tvlist.extensions

import android.util.Log
import org.json.JSONObject

fun String.toAuthToken() = "Bearer $this"

fun String.toJSONObject() = try {
    JSONObject(
        this
    )
} catch (e: Exception) {
    Log.d("String", "toJSONObject: ERROR_PARSE: ${e.message}")
    null
}