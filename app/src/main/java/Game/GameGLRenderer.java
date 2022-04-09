package Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.example.mysprout.R;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GameGLRenderer implements GLSurfaceView.Renderer {
    public static int width;
    public static int height;

    private SpriteRenderer mSpriteRenderer;
    private Context mContext;

    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    public GameGLRenderer(Context context){
        super();
        mContext = context;
    }
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.0f,0.0f,0.0f,1.0f);
        mSpriteRenderer = new SpriteRenderer();
        Bitmap image = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.mountain02);

        mSpriteRenderer.initTexture(image);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        GameManager.getInstance().Draw(vPMatrix);
        mSpriteRenderer.Draw(vPMatrix);
    }





    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GameGLRenderer.width = width;
        GameGLRenderer.height = height;

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
