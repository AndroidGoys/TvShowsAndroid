package good.damn.tvlist.extensions

import good.damn.shaderblur.views.BlurShaderView

fun BlurShaderView.pause() {
    stopRenderLoop()
    onPause()
}

fun BlurShaderView.resume() {
    startRenderLoop()
    onResume()
}