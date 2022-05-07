package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the User type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Users", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "undefined", fields = {"id"})
public final class User implements Model {
  public static final QueryField ID = field("User", "id");
  public static final QueryField GAME_SCORE = field("User", "game_score");
  public static final QueryField TRANSPORTATION = field("User", "transportation");
  public static final QueryField FOOD = field("User", "food");
  public static final QueryField ACTION = field("User", "action");
  public static final QueryField NICKNAME = field("User", "nickname");
  public static final QueryField SPROUT_NAME = field("User", "sprout_name");
  public static final QueryField SPROUT_EXP = field("User", "sprout_exp");
  public static final QueryField CARBON_SAVE = field("User", "carbon_save");
  public static final QueryField MEAT_CARBON = field("User", "meat_carbon");
  public static final QueryField TRANSPORTATION_CARBON = field("User", "transportation_carbon");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="GameScore") GameScore game_score;
  private final @ModelField(targetType="ID", isRequired = true) List<String> transportation;
  private final @ModelField(targetType="ID", isRequired = true) List<String> food;
  private final @ModelField(targetType="ID", isRequired = true) List<String> action;
  private final @ModelField(targetType="String", isRequired = true) String nickname;
  private final @ModelField(targetType="String", isRequired = true) String sprout_name;
  private final @ModelField(targetType="Int", isRequired = true) Integer sprout_exp;
  private final @ModelField(targetType="Int", isRequired = true) Integer carbon_save;
  private final @ModelField(targetType="Int") Integer meat_carbon;
  private final @ModelField(targetType="Int") Integer transportation_carbon;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public GameScore getGameScore() {
      return game_score;
  }
  
  public List<String> getTransportation() {
      return transportation;
  }
  
  public List<String> getFood() {
      return food;
  }
  
  public List<String> getAction() {
      return action;
  }
  
  public String getNickname() {
      return nickname;
  }
  
  public String getSproutName() {
      return sprout_name;
  }
  
  public Integer getSproutExp() {
      return sprout_exp;
  }
  
  public Integer getCarbonSave() {
      return carbon_save;
  }
  
  public Integer getMeatCarbon() {
      return meat_carbon;
  }
  
  public Integer getTransportationCarbon() {
      return transportation_carbon;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private User(String id, GameScore game_score, List<String> transportation, List<String> food, List<String> action, String nickname, String sprout_name, Integer sprout_exp, Integer carbon_save, Integer meat_carbon, Integer transportation_carbon) {
    this.id = id;
    this.game_score = game_score;
    this.transportation = transportation;
    this.food = food;
    this.action = action;
    this.nickname = nickname;
    this.sprout_name = sprout_name;
    this.sprout_exp = sprout_exp;
    this.carbon_save = carbon_save;
    this.meat_carbon = meat_carbon;
    this.transportation_carbon = transportation_carbon;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      User user = (User) obj;
      return ObjectsCompat.equals(getId(), user.getId()) &&
              ObjectsCompat.equals(getGameScore(), user.getGameScore()) &&
              ObjectsCompat.equals(getTransportation(), user.getTransportation()) &&
              ObjectsCompat.equals(getFood(), user.getFood()) &&
              ObjectsCompat.equals(getAction(), user.getAction()) &&
              ObjectsCompat.equals(getNickname(), user.getNickname()) &&
              ObjectsCompat.equals(getSproutName(), user.getSproutName()) &&
              ObjectsCompat.equals(getSproutExp(), user.getSproutExp()) &&
              ObjectsCompat.equals(getCarbonSave(), user.getCarbonSave()) &&
              ObjectsCompat.equals(getMeatCarbon(), user.getMeatCarbon()) &&
              ObjectsCompat.equals(getTransportationCarbon(), user.getTransportationCarbon()) &&
              ObjectsCompat.equals(getCreatedAt(), user.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), user.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getGameScore())
      .append(getTransportation())
      .append(getFood())
      .append(getAction())
      .append(getNickname())
      .append(getSproutName())
      .append(getSproutExp())
      .append(getCarbonSave())
      .append(getMeatCarbon())
      .append(getTransportationCarbon())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("User {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("game_score=" + String.valueOf(getGameScore()) + ", ")
      .append("transportation=" + String.valueOf(getTransportation()) + ", ")
      .append("food=" + String.valueOf(getFood()) + ", ")
      .append("action=" + String.valueOf(getAction()) + ", ")
      .append("nickname=" + String.valueOf(getNickname()) + ", ")
      .append("sprout_name=" + String.valueOf(getSproutName()) + ", ")
      .append("sprout_exp=" + String.valueOf(getSproutExp()) + ", ")
      .append("carbon_save=" + String.valueOf(getCarbonSave()) + ", ")
      .append("meat_carbon=" + String.valueOf(getMeatCarbon()) + ", ")
      .append("transportation_carbon=" + String.valueOf(getTransportationCarbon()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TransportationStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static User justId(String id) {
    return new User(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      game_score,
      transportation,
      food,
      action,
      nickname,
      sprout_name,
      sprout_exp,
      carbon_save,
      meat_carbon,
      transportation_carbon);
  }
  public interface TransportationStep {
    FoodStep transportation(List<String> transportation);
  }
  

  public interface FoodStep {
    ActionStep food(List<String> food);
  }
  

  public interface ActionStep {
    NicknameStep action(List<String> action);
  }
  

  public interface NicknameStep {
    SproutNameStep nickname(String nickname);
  }
  

  public interface SproutNameStep {
    SproutExpStep sproutName(String sproutName);
  }
  

  public interface SproutExpStep {
    CarbonSaveStep sproutExp(Integer sproutExp);
  }
  

  public interface CarbonSaveStep {
    BuildStep carbonSave(Integer carbonSave);
  }
  

  public interface BuildStep {
    User build();
    BuildStep id(String id);
    BuildStep gameScore(GameScore gameScore);
    BuildStep meatCarbon(Integer meatCarbon);
    BuildStep transportationCarbon(Integer transportationCarbon);
  }
  

  public static class Builder implements TransportationStep, FoodStep, ActionStep, NicknameStep, SproutNameStep, SproutExpStep, CarbonSaveStep, BuildStep {
    private String id;
    private List<String> transportation;
    private List<String> food;
    private List<String> action;
    private String nickname;
    private String sprout_name;
    private Integer sprout_exp;
    private Integer carbon_save;
    private GameScore game_score;
    private Integer meat_carbon;
    private Integer transportation_carbon;
    @Override
     public User build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new User(
          id,
          game_score,
          transportation,
          food,
          action,
          nickname,
          sprout_name,
          sprout_exp,
          carbon_save,
          meat_carbon,
          transportation_carbon);
    }
    
    @Override
     public FoodStep transportation(List<String> transportation) {
        Objects.requireNonNull(transportation);
        this.transportation = transportation;
        return this;
    }
    
    @Override
     public ActionStep food(List<String> food) {
        Objects.requireNonNull(food);
        this.food = food;
        return this;
    }
    
    @Override
     public NicknameStep action(List<String> action) {
        Objects.requireNonNull(action);
        this.action = action;
        return this;
    }
    
    @Override
     public SproutNameStep nickname(String nickname) {
        Objects.requireNonNull(nickname);
        this.nickname = nickname;
        return this;
    }
    
    @Override
     public SproutExpStep sproutName(String sproutName) {
        Objects.requireNonNull(sproutName);
        this.sprout_name = sproutName;
        return this;
    }
    
    @Override
     public CarbonSaveStep sproutExp(Integer sproutExp) {
        Objects.requireNonNull(sproutExp);
        this.sprout_exp = sproutExp;
        return this;
    }
    
    @Override
     public BuildStep carbonSave(Integer carbonSave) {
        Objects.requireNonNull(carbonSave);
        this.carbon_save = carbonSave;
        return this;
    }
    
    @Override
     public BuildStep gameScore(GameScore gameScore) {
        this.game_score = gameScore;
        return this;
    }
    
    @Override
     public BuildStep meatCarbon(Integer meatCarbon) {
        this.meat_carbon = meatCarbon;
        return this;
    }
    
    @Override
     public BuildStep transportationCarbon(Integer transportationCarbon) {
        this.transportation_carbon = transportationCarbon;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, GameScore gameScore, List<String> transportation, List<String> food, List<String> action, String nickname, String sproutName, Integer sproutExp, Integer carbonSave, Integer meatCarbon, Integer transportationCarbon) {
      super.id(id);
      super.transportation(transportation)
        .food(food)
        .action(action)
        .nickname(nickname)
        .sproutName(sproutName)
        .sproutExp(sproutExp)
        .carbonSave(carbonSave)
        .gameScore(gameScore)
        .meatCarbon(meatCarbon)
        .transportationCarbon(transportationCarbon);
    }
    
    @Override
     public CopyOfBuilder transportation(List<String> transportation) {
      return (CopyOfBuilder) super.transportation(transportation);
    }
    
    @Override
     public CopyOfBuilder food(List<String> food) {
      return (CopyOfBuilder) super.food(food);
    }
    
    @Override
     public CopyOfBuilder action(List<String> action) {
      return (CopyOfBuilder) super.action(action);
    }
    
    @Override
     public CopyOfBuilder nickname(String nickname) {
      return (CopyOfBuilder) super.nickname(nickname);
    }
    
    @Override
     public CopyOfBuilder sproutName(String sproutName) {
      return (CopyOfBuilder) super.sproutName(sproutName);
    }
    
    @Override
     public CopyOfBuilder sproutExp(Integer sproutExp) {
      return (CopyOfBuilder) super.sproutExp(sproutExp);
    }
    
    @Override
     public CopyOfBuilder carbonSave(Integer carbonSave) {
      return (CopyOfBuilder) super.carbonSave(carbonSave);
    }
    
    @Override
     public CopyOfBuilder gameScore(GameScore gameScore) {
      return (CopyOfBuilder) super.gameScore(gameScore);
    }
    
    @Override
     public CopyOfBuilder meatCarbon(Integer meatCarbon) {
      return (CopyOfBuilder) super.meatCarbon(meatCarbon);
    }
    
    @Override
     public CopyOfBuilder transportationCarbon(Integer transportationCarbon) {
      return (CopyOfBuilder) super.transportationCarbon(transportationCarbon);
    }
  }
  
}
