package good.damn.tvlist.extensions

import org.json.JSONArray

fun JSONArray.getAt(
    index: Int
) = try {
    get(index)
} catch (e: Exception) {
    null
}