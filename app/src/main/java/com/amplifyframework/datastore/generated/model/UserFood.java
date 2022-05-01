package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasOne;
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

/** This is an auto generated class representing the UserFood type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "UserFoods", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "undefined", fields = {"id"})
public final class UserFood implements Model {
  public static final QueryField ID = field("UserFood", "id");
  public static final QueryField FOOD_DATA_ID = field("UserFood", "food_data_id");
  public static final QueryField COUNT = field("UserFood", "count");
  public static final QueryField USER_FOOD_ID = field("UserFood", "userFoodId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String food_data_id;
  private final @ModelField(targetType="FoodData") @HasOne(associatedWith = "id", type = FoodData.class) FoodData data = null;
  private final @ModelField(targetType="Int") List<Integer> count;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="ID") String userFoodId;
  public String getId() {
      return id;
  }
  
  public String getFoodDataId() {
      return food_data_id;
  }
  
  public FoodData getData() {
      return data;
  }
  
  public List<Integer> getCount() {
      return count;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  public String getUserFoodId() {
      return userFoodId;
  }
  
  private UserFood(String id, String food_data_id, List<Integer> count, String userFoodId) {
    this.id = id;
    this.food_data_id = food_data_id;
    this.count = count;
    this.userFoodId = userFoodId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      UserFood userFood = (UserFood) obj;
      return ObjectsCompat.equals(getId(), userFood.getId()) &&
              ObjectsCompat.equals(getFoodDataId(), userFood.getFoodDataId()) &&
              ObjectsCompat.equals(getCount(), userFood.getCount()) &&
              ObjectsCompat.equals(getCreatedAt(), userFood.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), userFood.getUpdatedAt()) &&
              ObjectsCompat.equals(getUserFoodId(), userFood.getUserFoodId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getFoodDataId())
      .append(getCount())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getUserFoodId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("UserFood {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("food_data_id=" + String.valueOf(getFoodDataId()) + ", ")
      .append("count=" + String.valueOf(getCount()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("userFoodId=" + String.valueOf(getUserFoodId()))
      .append("}")
      .toString();
  }
  
  public static FoodDataIdStep builder() {
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
  public static UserFood justId(String id) {
    return new UserFood(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      food_data_id,
      count,
      userFoodId);
  }
  public interface FoodDataIdStep {
    BuildStep foodDataId(String foodDataId);
  }
  

  public interface BuildStep {
    UserFood build();
    BuildStep id(String id);
    BuildStep count(List<Integer> count);
    BuildStep userFoodId(String userFoodId);
  }
  

  public static class Builder implements FoodDataIdStep, BuildStep {
    private String id;
    private String food_data_id;
    private List<Integer> count;
    private String userFoodId;
    @Override
     public UserFood build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new UserFood(
          id,
          food_data_id,
          count,
          userFoodId);
    }
    
    @Override
     public BuildStep foodDataId(String foodDataId) {
        Objects.requireNonNull(foodDataId);
        this.food_data_id = foodDataId;
        return this;
    }
    
    @Override
     public BuildStep count(List<Integer> count) {
        this.count = count;
        return this;
    }
    
    @Override
     public BuildStep userFoodId(String userFoodId) {
        this.userFoodId = userFoodId;
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
    private CopyOfBuilder(String id, String foodDataId, List<Integer> count, String userFoodId) {
      super.id(id);
      super.foodDataId(foodDataId)
        .count(count)
        .userFoodId(userFoodId);
    }
    
    @Override
     public CopyOfBuilder foodDataId(String foodDataId) {
      return (CopyOfBuilder) super.foodDataId(foodDataId);
    }
    
    @Override
     public CopyOfBuilder count(List<Integer> count) {
      return (CopyOfBuilder) super.count(count);
    }
    
    @Override
     public CopyOfBuilder userFoodId(String userFoodId) {
      return (CopyOfBuilder) super.userFoodId(userFoodId);
    }
  }
  
}
