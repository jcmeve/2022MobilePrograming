package Game;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.sql.Struct;

import javax.microedition.khronos.opengles.GL;

public class Triangle {
    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "void main() {" +
            "gl_Position = uMVPMatrix * vPosition;" +
            "}";

    private int vPMatrixHangle;
    private final String fragmentShaderCode =
            "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "gl_FragColor = vColor;" +
            "}";

    private final int mProgram;

    private FloatBuffer vertexBuffer;

    static final int COORDS_PER_VERTEX = 3;
    static float[] triangleCoords = {
            0.0f,  0.622008459f, 0.0f, // top
            -0.5f, -0.311004243f, 0.0f, // bottom left
            0.5f, -0.311004243f, 0.0f  // bottom right
    };

    float[] color = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    public Triangle(){


        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length*4);
        bb.order(ByteOrder.nativeOrder());

        vertexBuffer = bb.asFloatBuffer();

        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);


        int vertexShader = GameGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);

        int fragmentShader = GameGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);

        GLES20.glLinkProgram(mProgram);
    }

    private int positionHandle;
    private int colorHandle;

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4;

    public void Draw(float[] mvpMatrix){
        GLES20.glUseProgram(mProgram);

        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(colorHandle, 1, color, 0);

        vPMatrixHangle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(vPMatrixHangle, 1, false, mvpMatrix, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        GLES20.glDisableVertexAttribArray(positionHandle);
    }


}
