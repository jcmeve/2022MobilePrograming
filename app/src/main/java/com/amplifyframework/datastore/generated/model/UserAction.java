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
  public static final QueryField ACTION_DATA_ID = field("UserAction", "action_data_id");
  public static final QueryField COUNT = field("UserAction", "count");
  public static final QueryField USER_ACTION_ID = field("UserAction", "userActionId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String action_data_id;
  private final @ModelField(targetType="ActionData") @HasOne(associatedWith = "id", type = ActionData.class) ActionData data = null;
  private final @ModelField(targetType="Int") List<Integer> count;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="ID") String userActionId;
  public String getId() {
      return id;
  }
  
  public String getActionDataId() {
      return action_data_id;
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
  
  public String getUserActionId() {
      return userActionId;
  }
  
  private UserAction(String id, String action_data_id, List<Integer> count, String userActionId) {
    this.id = id;
    this.action_data_id = action_data_id;
    this.count = count;
    this.userActionId = userActionId;
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
              ObjectsCompat.equals(getActionDataId(), userAction.getActionDataId()) &&
              ObjectsCompat.equals(getCount(), userAction.getCount()) &&
              ObjectsCompat.equals(getCreatedAt(), userAction.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), userAction.getUpdatedAt()) &&
              ObjectsCompat.equals(getUserActionId(), userAction.getUserActionId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getActionDataId())
      .append(getCount())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getUserActionId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("UserAction {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("action_data_id=" + String.valueOf(getActionDataId()) + ", ")
      .append("count=" + String.valueOf(getCount()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("userActionId=" + String.valueOf(getUserActionId()))
      .append("}")
      .toString();
  }
  
  public static ActionDataIdStep builder() {
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
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      action_data_id,
      count,
      userActionId);
  }
  public interface ActionDataIdStep {
    BuildStep actionDataId(String actionDataId);
  }
  

  public interface BuildStep {
    UserAction build();
    BuildStep id(String id);
    BuildStep count(List<Integer> count);
    BuildStep userActionId(String userActionId);
  }
  

  public static class Builder implements ActionDataIdStep, BuildStep {
    private String id;
    private String action_data_id;
    private List<Integer> count;
    private String userActionId;
    @Override
     public UserAction build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new UserAction(
          id,
          action_data_id,
          count,
          userActionId);
    }
    
    @Override
     public BuildStep actionDataId(String actionDataId) {
        Objects.requireNonNull(actionDataId);
        this.action_data_id = actionDataId;
        return this;
    }
    
    @Override
     public BuildStep count(List<Integer> count) {
        this.count = count;
        return this;
    }
    
    @Override
     public BuildStep userActionId(String userActionId) {
        this.userActionId = userActionId;
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
    private CopyOfBuilder(String id, String actionDataId, List<Integer> count, String userActionId) {
      super.id(id);
      super.actionDataId(actionDataId)
        .count(count)
        .userActionId(userActionId);
    }
    
    @Override
     public CopyOfBuilder actionDataId(String actionDataId) {
      return (CopyOfBuilder) super.actionDataId(actionDataId);
    }
    
    @Override
     public CopyOfBuilder count(List<Integer> count) {
      return (CopyOfBuilder) super.count(count);
    }
    
    @Override
     public CopyOfBuilder userActionId(String userActionId) {
      return (CopyOfBuilder) super.userActionId(userActionId);
    }
  }
  
}
