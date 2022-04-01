package Game;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.text.BreakIterator;

import javax.microedition.khronos.opengles.GL;

public class Square {
    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "attribute vec2 inUV;" +
            "varying vec2 vUV;" +
            "void main() {" +
                "vUV = inUV;" +
                "gl_Position = uMVPMatrix * vPosition;" +
            "}";

    private int vPMatrixHangle;
    private final String fragmentShaderCode =
            "precision mediump float;" +
            "varying vec2 vUV;" +
            "uniform sampler2D u_texture;" +
            "void main() {" +
                "gl_FragColor = texture2D(u_texture ,vUV);" +
            "}";

    private final int mProgram;



    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;
    private FloatBuffer uvBuffer;

    static final  int COORDS_PER_VERTEX = 3;

    static float[] squareCoords = {
            -0.5f, 0.5f, 0.0f,   // top left
            -0.5f, -0.5f, 0.0f,   // bottom left
            0.5f, -0.5f, 0.0f,   // bottom right
            0.5f, 0.5f, 0.0f  // top right
    };

    private  float[] uvCoords ={
            0.0f,0.0f,
            0.0f,1.0f,
            1.0f,1.0f,
            1.0f,0.0f
    };
    private short[] drawOrder = {0,1,2,0,2,3};

    public Square(){
        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);

        bb.order(ByteOrder.nativeOrder());

        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);

        dlb.order(ByteOrder.nativeOrder());

        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        ByteBuffer uvb = ByteBuffer.allocateDirect(uvCoords.length * 4);
        uvb.order(ByteOrder.nativeOrder());

        uvBuffer = uvb.asFloatBuffer();
        uvBuffer.put(uvCoords);
        uvBuffer.position(0);




        int vertexShader = GameGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);

        int fragmentShader = GameGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);

        GLES20.glLinkProgram(mProgram);
    }



    private int positionHandle;
    private int uvHandle;
    private int textureHandle;

    private final int vertexCount = squareCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4;


    public void Draw(float[] mvpMatrix){
        GLES20.glUseProgram(mProgram);
        GLES20.glUniform1i(textureHandle, 0);

        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        textureHandle = GLES20.glGetUniformLocation(mProgram, "u_texture");
        GLES20.glEnableVertexAttribArray(textureHandle);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureName);



        uvHandle = GLES20.glGetAttribLocation(mProgram, "inUV");
        GLES20.glVertexAttribPointer(uvHandle, 2, GLES20.GL_FLOAT, false, 0, uvBuffer);



        vPMatrixHangle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(vPMatrixHangle, 1, false, mvpMatrix, 0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

        GLES20.glDisableVertexAttribArray(textureHandle);
        GLES20.glDisableVertexAttribArray(positionHandle);
    }

    int mTextureName;

    public void initTexture(Bitmap image) {
        int[] name = new int[1];

        GLES20.glGenTextures(1, name, 0);

        if ( name[0] > 0 ) {

            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, name[0]);


            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);

            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);

            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
            // use gl utils to load texture by Bitmap

            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, image, 0);
            // texture binding to 0

            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

            mTextureName = name[0];

        }

    }



}
