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
  public static final QueryField FOOD_NAME = field("UserFood", "food_name");
  public static final QueryField COUNT = field("UserFood", "count");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String food_name;
  private final @ModelField(targetType="FoodData") @HasOne(associatedWith = "name", type = FoodData.class) FoodData data = null;
  private final @ModelField(targetType="Int", isRequired = true) List<Integer> count;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getFoodName() {
      return food_name;
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
  
  private UserFood(String id, String food_name, List<Integer> count) {
    this.id = id;
    this.food_name = food_name;
    this.count = count;
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
              ObjectsCompat.equals(getFoodName(), userFood.getFoodName()) &&
              ObjectsCompat.equals(getCount(), userFood.getCount()) &&
              ObjectsCompat.equals(getCreatedAt(), userFood.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), userFood.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getFoodName())
      .append(getCount())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("UserFood {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("food_name=" + String.valueOf(getFoodName()) + ", ")
      .append("count=" + String.valueOf(getCount()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static FoodNameStep builder() {
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
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      food_name,
      count);
  }
  public interface FoodNameStep {
    CountStep foodName(String foodName);
  }
  

  public interface CountStep {
    BuildStep count(List<Integer> count);
  }
  

  public interface BuildStep {
    UserFood build();
    BuildStep id(String id);
  }
  

  public static class Builder implements FoodNameStep, CountStep, BuildStep {
    private String id;
    private String food_name;
    private List<Integer> count;
    @Override
     public UserFood build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new UserFood(
          id,
          food_name,
          count);
    }
    
    @Override
     public CountStep foodName(String foodName) {
        Objects.requireNonNull(foodName);
        this.food_name = foodName;
        return this;
    }
    
    @Override
     public BuildStep count(List<Integer> count) {
        Objects.requireNonNull(count);
        this.count = count;
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
    private CopyOfBuilder(String id, String foodName, List<Integer> count) {
      super.id(id);
      super.foodName(foodName)
        .count(count);
    }
    
    @Override
     public CopyOfBuilder foodName(String foodName) {
      return (CopyOfBuilder) super.foodName(foodName);
    }
    
    @Override
     public CopyOfBuilder count(List<Integer> count) {
      return (CopyOfBuilder) super.count(count);
    }
  }
  
}
