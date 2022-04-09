package Game;

public abstract class GameComponent {
    public GameObject gameObject;

    public abstract ComponentType getComponentType();

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public void Destroy(){
        gameObject.RemoveComponent(this);
        gameObject = null;
    }

    public enum ComponentType{
      SpriteRenderer,
    }
    public abstract void Update(float deltaTime);
}
