package Game;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameObject {
    private int id;

    public GameObject(){
        GameManager.getInstance().AddGameObject(this);
    }

    public void setId(int _id){
        id = _id;
    }

    public void AddComponent(GameComponent gameComponent){
        gameComponent.setGameObject(this);
        GameManager.getInstance().AddComponent(id, gameComponent);
    }

    public void RemoveComponent(GameComponent gameComponent){
        GameManager.getInstance().RemoveComponent(id, gameComponent);
    }

    public GameComponent GetComponent(GameComponent.ComponentType type){
        return GameManager.getInstance().GetComponent(id,type);
    }
}
