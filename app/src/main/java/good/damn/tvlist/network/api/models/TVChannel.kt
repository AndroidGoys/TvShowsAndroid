package good.damn.tvlist.network.api.models

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.log

data class TVChannel(
    val name: String,
    val shortName: String? = null,
    val imageUrl: String? = null,
    val programs: Array<TVProgram>? = null
) {
    companion object {
        private const val TAG = "TVChannel"
        fun createFromJSON(
            json: JSONObject
        ): TVChannel? {
            val name = try {
                json.get(
                    "name_ru"
                ) as String
            } catch (e: Exception) {
                Log.d(TAG, "createFromJSON: NO NAME ${e.message}")
                return null
            }

            val imageUrl = try {
                json.get(
                    "image"
                ) as String
            } catch (e: Exception) {
                Log.d(TAG, "createFromJSON: NO PREVIEW ${e.message}")
                null
            }

            val shortName = if (name.length >= 21)
                name.substring(0,21) + "…"
            else null

            val current = try {
                json.getJSONArray(
                    "current"
                )
            } catch (e: Exception) {
                Log.d(TAG, "createFromJSON: ERROR_CURRENT: ${e.message}")
                null
            }

            if (current == null) {
                return TVChannel(
                    name,
                    shortName,
                    imageUrl
                )
            }

            val tvProgram = Array(current.length()) {
                TVProgram.createFromJSON(
                    current.getJSONObject(it)
                )
            }

            return TVChannel(
                name,
                shortName,
                imageUrl,
                tvProgram
            )
        }
    }

}