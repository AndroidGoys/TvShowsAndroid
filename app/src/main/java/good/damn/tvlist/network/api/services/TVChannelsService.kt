package good.damn.tvlist.network.api.services

import android.content.Context
import android.util.Log
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.readBytes
import good.damn.tvlist.network.api.models.TVChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset
import kotlin.math.log

class TVChannelsService {

    companion object {
        private const val TAG = "TVChannelsService"
        private const val URL = "https://limehd.tv/api/v4/playlist"
    }

    private val mClient = OkHttpClient()

    private var mJsonChannels: JSONArray? = null

    fun loadChannels(
        context: Context
    ) {
        context.resources.openRawResource(
            R.raw.playlist
        ).readBytes(
            8192
        ).let {
            val json = String(
                it,
                Charset.forName("UTF-8")
            )

            mJsonChannels = processJsonChannels(json)
        }
    }

    fun getChannels(
        from: Int,
        limit: Int,
        completion: (ArrayList<TVChannel?>) -> Unit
    ) {
        if (mJsonChannels == null) {
            return
        }
        CoroutineScope(
            Dispatchers.IO
        ).launch {
            /*val response = try {
                mClient
                    .newCall(
                        Request.Builder()
                            .url("$URL?page=1&limit=2")
                            .get().build()
                    ).execute()
            } catch (e: Exception) {
                Log.d(TAG, "getChannels: ERROR_EXECUTE: ${e.message}")
                return@launch
            }

            val json = response
                .body?.string()
                ?: return@launch

            Log.d(TAG, "getChannels: RESPONSE: $response $json")
            
            if (response.code != 200) {
                Log.d(TAG, "getChannels: WRONG_CODE")
                return@launch
            }*/

            val tvChannels = ArrayList<TVChannel?>(
                limit
            )

            for (i in 0..<limit) {
                tvChannels.add(
                    TVChannel.createFromJSON(
                        mJsonChannels!!.getJSONObject(
                            from - 1 + i
                        )
                    )
                )
            }

            App.ui {
                completion.invoke(
                    tvChannels
                )
            }
        }
    }

    private fun processJsonChannels(
        json: String
    ): JSONArray? {

        val obj = try {
            JSONObject(json)
        } catch (e: Exception) {
            Log.d(TAG, "processJsonChannels: NO_JSON_OBJECT: ${e.message}")
            return null
        }

        val channels = try {
            obj.getJSONArray(
                "channels"
            )
        } catch (e: Exception) {
            Log.d(TAG, "processJsonChannels: NO_CHANNELS: ${e.message}")
            return null
        }

        Log.d(TAG, "processJsonChannels: CHANNELS_COUNT: ${channels.length()}")

        return channels
    }

}