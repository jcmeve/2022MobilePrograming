package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasMany;
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
  public static final QueryField EMAIL = field("User", "email");
  public static final QueryField NICKNAME = field("User", "nickname");
  public static final QueryField SPROUT_NAME = field("User", "sprout_name");
  public static final QueryField SPROUT_EXP = field("User", "sprout_exp");
  public static final QueryField CARBON_SAVE = field("User", "carbon_save");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="GameScore") GameScore game_score;
  private final @ModelField(targetType="UserTransportation") @HasMany(associatedWith = "userTransportationId", type = UserTransportation.class) List<UserTransportation> transportation = null;
  private final @ModelField(targetType="UserFood") @HasMany(associatedWith = "userFoodId", type = UserFood.class) List<UserFood> food = null;
  private final @ModelField(targetType="UserAction") @HasMany(associatedWith = "userActionId", type = UserAction.class) List<UserAction> action = null;
  private final @ModelField(targetType="AWSEmail", isRequired = true) String email;
  private final @ModelField(targetType="String", isRequired = true) String nickname;
  private final @ModelField(targetType="String", isRequired = true) String sprout_name;
  private final @ModelField(targetType="Int", isRequired = true) Integer sprout_exp;
  private final @ModelField(targetType="Int", isRequired = true) Integer carbon_save;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public GameScore getGameScore() {
      return game_score;
  }
  
  public List<UserTransportation> getTransportation() {
      return transportation;
  }
  
  public List<UserFood> getFood() {
      return food;
  }
  
  public List<UserAction> getAction() {
      return action;
  }
  
  public String getEmail() {
      return email;
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
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private User(String id, GameScore game_score, String email, String nickname, String sprout_name, Integer sprout_exp, Integer carbon_save) {
    this.id = id;
    this.game_score = game_score;
    this.email = email;
    this.nickname = nickname;
    this.sprout_name = sprout_name;
    this.sprout_exp = sprout_exp;
    this.carbon_save = carbon_save;
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
              ObjectsCompat.equals(getEmail(), user.getEmail()) &&
              ObjectsCompat.equals(getNickname(), user.getNickname()) &&
              ObjectsCompat.equals(getSproutName(), user.getSproutName()) &&
              ObjectsCompat.equals(getSproutExp(), user.getSproutExp()) &&
              ObjectsCompat.equals(getCarbonSave(), user.getCarbonSave()) &&
              ObjectsCompat.equals(getCreatedAt(), user.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), user.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getGameScore())
      .append(getEmail())
      .append(getNickname())
      .append(getSproutName())
      .append(getSproutExp())
      .append(getCarbonSave())
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
      .append("email=" + String.valueOf(getEmail()) + ", ")
      .append("nickname=" + String.valueOf(getNickname()) + ", ")
      .append("sprout_name=" + String.valueOf(getSproutName()) + ", ")
      .append("sprout_exp=" + String.valueOf(getSproutExp()) + ", ")
      .append("carbon_save=" + String.valueOf(getCarbonSave()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static EmailStep builder() {
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
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      game_score,
      email,
      nickname,
      sprout_name,
      sprout_exp,
      carbon_save);
  }
  public interface EmailStep {
    NicknameStep email(String email);
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
  }
  

  public static class Builder implements EmailStep, NicknameStep, SproutNameStep, SproutExpStep, CarbonSaveStep, BuildStep {
    private String id;
    private String email;
    private String nickname;
    private String sprout_name;
    private Integer sprout_exp;
    private Integer carbon_save;
    private GameScore game_score;
    @Override
     public User build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new User(
          id,
          game_score,
          email,
          nickname,
          sprout_name,
          sprout_exp,
          carbon_save);
    }
    
    @Override
     public NicknameStep email(String email) {
        Objects.requireNonNull(email);
        this.email = email;
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
    private CopyOfBuilder(String id, GameScore gameScore, String email, String nickname, String sproutName, Integer sproutExp, Integer carbonSave) {
      super.id(id);
      super.email(email)
        .nickname(nickname)
        .sproutName(sproutName)
        .sproutExp(sproutExp)
        .carbonSave(carbonSave)
        .gameScore(gameScore);
    }
    
    @Override
     public CopyOfBuilder email(String email) {
      return (CopyOfBuilder) super.email(email);
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
  }
  
}
