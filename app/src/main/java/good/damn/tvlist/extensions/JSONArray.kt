package good.damn.tvlist.extensions

import org.json.JSONArray


fun JSONArray.getJSONAt(
    index: Int
) = try {
    getJSONObject(index)
} catch (e: Exception) {
    null
}

fun JSONArray.getAt(
    index: Int
) = try {
    get(index)
} catch (e: Exception) {
    null
}