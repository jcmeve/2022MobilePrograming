package Game;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class GameGLSurfaceView extends GLSurfaceView {
    private final GameGLRenderer renderer;
    public GameGLSurfaceView(Context context){
        super(context);

        setEGLContextClientVersion(2);

        renderer = new GameGLRenderer(context);

        setRenderer(renderer);
    }
}
