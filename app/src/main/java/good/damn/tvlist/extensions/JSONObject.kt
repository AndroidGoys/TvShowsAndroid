package good.damn.tvlist.extensions

import android.util.Log
import org.json.JSONObject

fun JSONObject.extract(
    key: String
) = try {
    get(key)
} catch (e: Exception) {
    Log.d("JSONObject", "extract: ERROR_GET: ${e.message}")
    null
}