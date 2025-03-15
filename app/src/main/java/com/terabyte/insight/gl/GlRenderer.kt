package com.terabyte.insight.gl

import android.opengl.GLSurfaceView.Renderer
import com.terabyte.insight.TEXT_NO_INFORMATION
import com.terabyte.insight.model.DeviceDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class GlRenderer(private val listener: (List<DeviceDetail>) -> Unit) : Renderer {

    override fun onSurfaceCreated(gl: GL10?, p1: EGLConfig?) {
        val gpuVendor = gl?.glGetString(GL10.GL_VENDOR) ?: TEXT_NO_INFORMATION
        val glVersion = gl?.glGetString(GL10.GL_VERSION) ?: TEXT_NO_INFORMATION
        val glExtensions = gl?.glGetString(GL10.GL_EXTENSIONS) ?: TEXT_NO_INFORMATION
        val glRenderer = gl?.glGetString(GL10.GL_RENDERER) ?: TEXT_NO_INFORMATION

        CoroutineScope(Dispatchers.Main).launch {
            listener(
                listOf(
                    DeviceDetail(
                        "GPU vendor",
                        gpuVendor,
                        "Company-manufacturer of graphic processor (GPU)"
                    ),
                    DeviceDetail(
                        "OpenGL version",
                        glVersion,
                        "Version of Open graphic library (OpenGL)"
                    ),
                    DeviceDetail(
                        "OpenGl extensions",
                        glExtensions,
                        "Open graphic library extensions"
                    ),
                    DeviceDetail(
                        "OpenGL renderer",
                        glRenderer,
                        ""
                    ),
                )
            )
        }
    }

    override fun onSurfaceChanged(gl: GL10?, p1: Int, p2: Int) {
    }

    override fun onDrawFrame(p0: GL10?) {
    }
}