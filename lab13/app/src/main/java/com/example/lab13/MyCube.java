package com.example.lab13;

import android.opengl.GLES20;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
public class MyCube {
    private final int mProgram;
    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;
    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoordss[] = {
            -0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,

            -0.5f, -0.5f, -0.5f,
            -0.5f, 0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
    };
    private short drawOrder[] = {
            1, 0, 2,
            2, 3, 1,

            3, 2, 6,
            6, 7, 3,

            1, 5, 4,
            4, 0, 1,

            2,0,4,
            4,6,2,

            5,1,3,
            3,7,5,

            4,5,7,
            7,6,4,


    }; // порядок рисования вершин
    float color[] = { 0.0f, 0.7f, 0.5f, 1.0f };
    public MyCube() {
        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoordss.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(triangleCoordss);
        vertexBuffer.position(0);
        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);
        // создание пустой OpenGL ES программы
        mProgram = GLES20.glCreateProgram();
        // Добавление вершинного шейдера в программу
        GLES20.glAttachShader(mProgram, vertexShader);
        // Добавление фрагментного шейдера в программу
        GLES20.glAttachShader(mProgram, fragmentShader);
        // Связывание программы
        GLES20.glLinkProgram(mProgram);
    }
    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    " gl_Position = uMVPMatrix * vPosition;" +
                    " gl_PointSize = 10.0; "+
                    "}";
    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    " gl_FragColor = vColor;" +
                    "}";
    private int positionHandle;
    private int colorHandle;
    private int vPMatrixHandle;
    private final int vertexCount = triangleCoordss.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 байта на вершину
    public void draw(float[] mvpMatrix) {
        // Добавляем программу в среду OpenGL ES
        GLES20.glUseProgram(mProgram);
        // Получаем элемент vPosition вершинного шейдера
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        // Покдлючаем массив вершин
        GLES20.glEnableVertexAttribArray(positionHandle);
        // Подготовка координат вершин
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);
        // Получаем элемент vColor фрагментного шейдера
        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        // Устанавливаем цвет для рисования
        GLES20.glUniform4fv(colorHandle, 1, color, 0);
        // Получаем матрицу преобразований
        vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        // Передаём матрицу преобразований проекции и вида камеры в шейдер
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0);
        // Рисуем куб
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, drawOrder.length,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
        // Рисуем точки на вершинах
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, vertexCount);
        // Отключаем массив вершин
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}