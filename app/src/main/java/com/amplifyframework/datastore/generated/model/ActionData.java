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

/** This is an auto generated class representing the ActionData type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "ActionData", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "undefined", fields = {"id"})
public final class ActionData implements Model {
  public static final QueryField ID = field("ActionData", "id");
  public static final QueryField NAME = field("ActionData", "name");
  public static final QueryField SAVE_CARBON = field("ActionData", "save_carbon");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="Int", isRequired = true) Integer save_carbon;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public Integer getSaveCarbon() {
      return save_carbon;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private ActionData(String id, String name, Integer save_carbon) {
    this.id = id;
    this.name = name;
    this.save_carbon = save_carbon;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      ActionData actionData = (ActionData) obj;
      return ObjectsCompat.equals(getId(), actionData.getId()) &&
              ObjectsCompat.equals(getName(), actionData.getName()) &&
              ObjectsCompat.equals(getSaveCarbon(), actionData.getSaveCarbon()) &&
              ObjectsCompat.equals(getCreatedAt(), actionData.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), actionData.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getSaveCarbon())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("ActionData {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("save_carbon=" + String.valueOf(getSaveCarbon()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
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
  public static ActionData justId(String id) {
    return new ActionData(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      save_carbon);
  }
  public interface NameStep {
    SaveCarbonStep name(String name);
  }
  

  public interface SaveCarbonStep {
    BuildStep saveCarbon(Integer saveCarbon);
  }
  

  public interface BuildStep {
    ActionData build();
    BuildStep id(String id);
  }
  

  public static class Builder implements NameStep, SaveCarbonStep, BuildStep {
    private String id;
    private String name;
    private Integer save_carbon;
    @Override
     public ActionData build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new ActionData(
          id,
          name,
          save_carbon);
    }
    
    @Override
     public SaveCarbonStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep saveCarbon(Integer saveCarbon) {
        Objects.requireNonNull(saveCarbon);
        this.save_carbon = saveCarbon;
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
    private CopyOfBuilder(String id, String name, Integer saveCarbon) {
      super.id(id);
      super.name(name)
        .saveCarbon(saveCarbon);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder saveCarbon(Integer saveCarbon) {
      return (CopyOfBuilder) super.saveCarbon(saveCarbon);
    }
  }
  
}
