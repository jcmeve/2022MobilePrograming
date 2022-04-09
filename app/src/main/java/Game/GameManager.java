package Game;

import android.util.Log;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Timer;

public class GameManager {
    private GameGLRenderer renderer;
    private static GameManager instance;
    public static GameManager getInstance() {
        return instance;
    }
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private ArrayList<ArrayList<GameComponent>> gameComponents = new ArrayList<>();
    private ArrayList<SpriteRenderer> spriteRenderers = new ArrayList<>();
    //private ArrayList<GameCollider> gameColliders = new ArrayList<GameCollider>();

    public static GameManager GMCreate(GameGLRenderer _renderer){
        if(instance != null)
            return null;
        instance = new GameManager(_renderer);
        instance.TestGame();
        return instance;
    }
    private void TestGame(){

    }

    private GameManager(GameGLRenderer _renderer){
        renderer = _renderer;
    }

    private final long FPS = 60;
    private final long FRAMEMILLIS = (long)1000/FPS;
    private final float FRAMESEC = (float)1 / 60;
    /*
    TODO : CHECK GAME PROGRAMING PATTERN
     */
    private void GameLoop(){
        long beforeTime = System.currentTimeMillis();
        while (true){
            long currentTIme = System.currentTimeMillis();
            long remainTime = (currentTIme - beforeTime);
            if(remainTime < FRAMEMILLIS) {
                try {
                    //noinspection BusyWait
                    Thread.sleep(remainTime);
                    continue;
                } catch (Exception e) {
                    Log.d("GAME", e.getLocalizedMessage());
                }
            }
            while (remainTime > FRAMEMILLIS) {
                Update(FRAMESEC);
                remainTime -= FRAMEMILLIS;
                beforeTime += FRAMEMILLIS;
            }

        }
    }

    private void Update(float deltiTIme){

    }
    public void Draw(final float[] vPMatrix){
        for(int i = 0; i < spriteRenderers.size();i++){
            spriteRenderers.get(i).Draw(vPMatrix);
        }
    }

    public void AddGameObject(GameObject gameObject){
        int i =0;
        for(; i<gameObjects.size();i++){
            if(gameObjects.get(i) == null){
                gameObjects.set(i, gameObject);
                gameComponents.set(i , new ArrayList<>());
                gameObject.setId(i);
            }
        }
        gameObjects.add(i, gameObject);
        gameComponents.add(i, new ArrayList<>());
        gameObject.setId(i);

    }

    public void AddComponent(int id, GameComponent gameComponent){

        gameComponents.get(id).add(gameComponent);
    }

    public void RemoveComponent(int id, GameComponent gameComponent){

    }

    public GameComponent GetComponent(int id, GameComponent.ComponentType type){
        ArrayList<GameComponent> target = gameComponents.get(id);
        for(int i = 0; i < target.size();i++){
            if(target.get(i).getComponentType() == type){
                return target.get(i);
            }
        }
        return null;
    }

}
