package good.damn.tvlist.network.api.models

import android.util.Log
import org.json.JSONObject
import kotlin.math.log

data class TVChannel(
    val name: String,
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



            return TVChannel(
                name,
                imageUrl
            )
        }
    }

}