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

/** This is an auto generated class representing the UserTransportation type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "UserTransportations", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "undefined", fields = {"id"})
public final class UserTransportation implements Model {
  public static final QueryField ID = field("UserTransportation", "id");
  public static final QueryField TRANSPORTATION_DATA_ID = field("UserTransportation", "transportation_data_id");
  public static final QueryField COUNT = field("UserTransportation", "count");
  public static final QueryField USER_TRANSPORTATION_ID = field("UserTransportation", "userTransportationId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String transportation_data_id;
  private final @ModelField(targetType="TransportationData") @HasOne(associatedWith = "id", type = TransportationData.class) TransportationData data = null;
  private final @ModelField(targetType="Int") List<Integer> count;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="ID") String userTransportationId;
  public String getId() {
      return id;
  }
  
  public String getTransportationDataId() {
      return transportation_data_id;
  }
  
  public TransportationData getData() {
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
  
  public String getUserTransportationId() {
      return userTransportationId;
  }
  
  private UserTransportation(String id, String transportation_data_id, List<Integer> count, String userTransportationId) {
    this.id = id;
    this.transportation_data_id = transportation_data_id;
    this.count = count;
    this.userTransportationId = userTransportationId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      UserTransportation userTransportation = (UserTransportation) obj;
      return ObjectsCompat.equals(getId(), userTransportation.getId()) &&
              ObjectsCompat.equals(getTransportationDataId(), userTransportation.getTransportationDataId()) &&
              ObjectsCompat.equals(getCount(), userTransportation.getCount()) &&
              ObjectsCompat.equals(getCreatedAt(), userTransportation.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), userTransportation.getUpdatedAt()) &&
              ObjectsCompat.equals(getUserTransportationId(), userTransportation.getUserTransportationId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTransportationDataId())
      .append(getCount())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getUserTransportationId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("UserTransportation {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("transportation_data_id=" + String.valueOf(getTransportationDataId()) + ", ")
      .append("count=" + String.valueOf(getCount()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("userTransportationId=" + String.valueOf(getUserTransportationId()))
      .append("}")
      .toString();
  }
  
  public static TransportationDataIdStep builder() {
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
  public static UserTransportation justId(String id) {
    return new UserTransportation(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      transportation_data_id,
      count,
      userTransportationId);
  }
  public interface TransportationDataIdStep {
    BuildStep transportationDataId(String transportationDataId);
  }
  

  public interface BuildStep {
    UserTransportation build();
    BuildStep id(String id);
    BuildStep count(List<Integer> count);
    BuildStep userTransportationId(String userTransportationId);
  }
  

  public static class Builder implements TransportationDataIdStep, BuildStep {
    private String id;
    private String transportation_data_id;
    private List<Integer> count;
    private String userTransportationId;
    @Override
     public UserTransportation build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new UserTransportation(
          id,
          transportation_data_id,
          count,
          userTransportationId);
    }
    
    @Override
     public BuildStep transportationDataId(String transportationDataId) {
        Objects.requireNonNull(transportationDataId);
        this.transportation_data_id = transportationDataId;
        return this;
    }
    
    @Override
     public BuildStep count(List<Integer> count) {
        this.count = count;
        return this;
    }
    
    @Override
     public BuildStep userTransportationId(String userTransportationId) {
        this.userTransportationId = userTransportationId;
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
    private CopyOfBuilder(String id, String transportationDataId, List<Integer> count, String userTransportationId) {
      super.id(id);
      super.transportationDataId(transportationDataId)
        .count(count)
        .userTransportationId(userTransportationId);
    }
    
    @Override
     public CopyOfBuilder transportationDataId(String transportationDataId) {
      return (CopyOfBuilder) super.transportationDataId(transportationDataId);
    }
    
    @Override
     public CopyOfBuilder count(List<Integer> count) {
      return (CopyOfBuilder) super.count(count);
    }
    
    @Override
     public CopyOfBuilder userTransportationId(String userTransportationId) {
      return (CopyOfBuilder) super.userTransportationId(userTransportationId);
    }
  }
  
}
