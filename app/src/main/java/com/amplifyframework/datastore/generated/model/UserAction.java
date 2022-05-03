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

/** This is an auto generated class representing the UserAction type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "UserActions", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "undefined", fields = {"id"})
public final class UserAction implements Model {
  public static final QueryField ID = field("UserAction", "id");
  public static final QueryField ACTION_NAME = field("UserAction", "action_name");
  public static final QueryField COUNT = field("UserAction", "count");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String action_name;
  private final @ModelField(targetType="ActionData") @HasOne(associatedWith = "name", type = ActionData.class) ActionData data = null;
  private final @ModelField(targetType="Int", isRequired = true) List<Integer> count;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getActionName() {
      return action_name;
  }
  
  public ActionData getData() {
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
  
  private UserAction(String id, String action_name, List<Integer> count) {
    this.id = id;
    this.action_name = action_name;
    this.count = count;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      UserAction userAction = (UserAction) obj;
      return ObjectsCompat.equals(getId(), userAction.getId()) &&
              ObjectsCompat.equals(getActionName(), userAction.getActionName()) &&
              ObjectsCompat.equals(getCount(), userAction.getCount()) &&
              ObjectsCompat.equals(getCreatedAt(), userAction.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), userAction.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getActionName())
      .append(getCount())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("UserAction {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("action_name=" + String.valueOf(getActionName()) + ", ")
      .append("count=" + String.valueOf(getCount()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static ActionNameStep builder() {
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
  public static UserAction justId(String id) {
    return new UserAction(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      action_name,
      count);
  }
  public interface ActionNameStep {
    CountStep actionName(String actionName);
  }
  

  public interface CountStep {
    BuildStep count(List<Integer> count);
  }
  

  public interface BuildStep {
    UserAction build();
    BuildStep id(String id);
  }
  

  public static class Builder implements ActionNameStep, CountStep, BuildStep {
    private String id;
    private String action_name;
    private List<Integer> count;
    @Override
     public UserAction build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new UserAction(
          id,
          action_name,
          count);
    }
    
    @Override
     public CountStep actionName(String actionName) {
        Objects.requireNonNull(actionName);
        this.action_name = actionName;
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
    private CopyOfBuilder(String id, String actionName, List<Integer> count) {
      super.id(id);
      super.actionName(actionName)
        .count(count);
    }
    
    @Override
     public CopyOfBuilder actionName(String actionName) {
      return (CopyOfBuilder) super.actionName(actionName);
    }
    
    @Override
     public CopyOfBuilder count(List<Integer> count) {
      return (CopyOfBuilder) super.count(count);
    }
  }
  
}
