package com.terabyte.insight.gl

import android.opengl.GLSurfaceView.Renderer
import com.terabyte.insight.TEXT_NO_INFORMATION
import com.terabyte.insight.model.DeviceDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class GlRenderer(private val listener: (List<DeviceDetail>) -> Unit): Renderer {

    override fun onSurfaceCreated(gl: GL10?, p1: EGLConfig?) {
        CoroutineScope(Dispatchers.Main).launch {
            listener(
                listOf(
                    DeviceDetail(
                        "GPU vendor",
                        gl?.glGetString(GL10.GL_VENDOR) ?: TEXT_NO_INFORMATION,
                        "Company-manufacturer of graphic processor (GPU)"
                    ),
                    DeviceDetail(
                        "OpenGL version",
                        gl?.glGetString(GL10.GL_VERSION) ?: TEXT_NO_INFORMATION,
                        "Version of Open graphic library (OpenGL)"
                    ),
                    DeviceDetail(
                        "OpenGl extensions",
                        gl?.glGetString(GL10.GL_EXTENSIONS) ?: TEXT_NO_INFORMATION,
                        "Open graphic library extensions"
                    ),
                    DeviceDetail(
                        "OpenGL renderer",
                        gl?.glGetString(GL10.GL_RENDERER) ?: TEXT_NO_INFORMATION,
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