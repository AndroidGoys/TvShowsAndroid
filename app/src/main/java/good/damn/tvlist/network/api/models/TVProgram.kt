package good.damn.tvlist.network.api.models

import android.util.Log
import good.damn.tvlist.network.api.models.enums.CensorAge
import org.json.JSONObject
import kotlin.random.Random

data class TVProgram(
    val channelId: Int = -1,
    val name: String = "",
    val censorAge: CensorAge = CensorAge.ADULT,
    val startTime: Long = -1,
    val rating: Float = 0.0f
) {

    companion object {
        private const val TAG = "TVProgram"

        fun createFromJSON(
            json: JSONObject
        ): TVProgram {

            val name = try {
                json.get(
                    "title"
                ) as String
            } catch (e: Exception) {
                Log.d(TAG, "createFromJSON: NO NAME TV_PROGRAM: ${e.message}")
                return TVProgram()
            }

            val startTime = try {
                json.get(
                    "timestart"
                ) as Long
            } catch (e: Exception) {
                Log.d(TAG, "createFromJSON: NO START TIME: ${e.message}")
                return TVProgram()
            }

            return TVProgram(
                0,
                name,
                CensorAge.ADULT,
                startTime,
                Random.nextFloat() * 5f
            )

        }
    }

}