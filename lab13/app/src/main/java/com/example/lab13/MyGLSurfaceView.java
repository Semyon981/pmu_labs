package com.example.lab13;

import android.content.Context;
import android.opengl.GLSurfaceView;
class MyGLSurfaceView extends GLSurfaceView {
    private final MyGLRenderer renderer;
    public MyGLSurfaceView(Context context){
        super(context);
        setEGLContextClientVersion(2); // Создаем контекст OpenGL ES 2.0
        renderer = new MyGLRenderer();
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}
