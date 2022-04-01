package Game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import com.example.mysprout.R;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

public class GameGLRenderer implements GLSurfaceView.Renderer {

    private Triangle mTriangle;
    private Square mSquare;
    private Context mContext;
    public GameGLRenderer(Context context){
        super();
        mContext = context;
    }
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.0f,0.0f,0.0f,1.0f);
        mTriangle = new Triangle();
        mSquare = new Square();
        Bitmap image = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.mountain02);
        mSquare.initTexture(image);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        //mTriangle.Draw(vPMatrix);
        mSquare.Draw(vPMatrix);
    }

    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];


    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0,0,width, height);
        float ratio = (float) width/height;

        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    public static int loadShader(int type, String shaderCode){
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
