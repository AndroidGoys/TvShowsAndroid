package good.damn.tvlist.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class TVChannelPlayerFragment
: StackFragment() {

    private var mPlayer: Player? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        mPlayer?.apply {
            prepare()
            play()
        }

    }

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ) = PlayerView(
        context
    ).apply {

        player = ExoPlayer.Builder(
            context
        ).build()

        setBackgroundColor(
            0xff000000.toInt()
        )

        val url = "http://qthttp.apple.com.edgesuite.net/1010qwoeiuryfg/sl.m3u8"

        val item = MediaItem.fromUri(
            Uri.parse(
                url
            )
        )

        player?.apply {
            setMediaItem(item)
        }

        mPlayer = player
    }

    override fun onDestroy() {
        super.onDestroy()
        mPlayer?.stop()
    }

}