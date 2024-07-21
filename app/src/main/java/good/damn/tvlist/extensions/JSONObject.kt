package good.damn.tvlist.extensions

import android.util.Log
import org.json.JSONObject

fun JSONObject.extractObject(
    key: String
) = try {
    getJSONObject(key)
} catch (e: Exception) {
    Log.d("JSONObject",
        "extractObject: ERROR_GET_OBJECT: ${e.message}"
    )
    null
}

fun JSONObject.extractArray(
    key: String
) = try {
    getJSONArray(key)
} catch (e: Exception) {
    Log.d(
        "JSONObject:",
        "extractArray: ERROR_GET_ARRAY: ${e.message}"
    )
    null
}

fun JSONObject.extract(
    key: String
) = try {
    get(key)
} catch (e: Exception) {
    Log.d("JSONObject", "extract: ERROR_GET: ${e.message}")
    null
}