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
  public static final QueryField TRANSPORTATION_NAME = field("UserTransportation", "transportation_name");
  public static final QueryField COUNT = field("UserTransportation", "count");
  public static final QueryField DATE = field("UserTransportation", "date");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String transportation_name;
  private final @ModelField(targetType="TransportationData") @HasOne(associatedWith = "name", type = TransportationData.class) TransportationData data = null;
  private final @ModelField(targetType="Float", isRequired = true) List<Double> count;
  private final @ModelField(targetType="AWSTimestamp", isRequired = true) List<Temporal.Timestamp> date;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getTransportationName() {
      return transportation_name;
  }
  
  public TransportationData getData() {
      return data;
  }
  
  public List<Double> getCount() {
      return count;
  }
  
  public List<Temporal.Timestamp> getDate() {
      return date;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private UserTransportation(String id, String transportation_name, List<Double> count, List<Temporal.Timestamp> date) {
    this.id = id;
    this.transportation_name = transportation_name;
    this.count = count;
    this.date = date;
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
              ObjectsCompat.equals(getTransportationName(), userTransportation.getTransportationName()) &&
              ObjectsCompat.equals(getCount(), userTransportation.getCount()) &&
              ObjectsCompat.equals(getDate(), userTransportation.getDate()) &&
              ObjectsCompat.equals(getCreatedAt(), userTransportation.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), userTransportation.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTransportationName())
      .append(getCount())
      .append(getDate())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("UserTransportation {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("transportation_name=" + String.valueOf(getTransportationName()) + ", ")
      .append("count=" + String.valueOf(getCount()) + ", ")
      .append("date=" + String.valueOf(getDate()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TransportationNameStep builder() {
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
      transportation_name,
      count,
      date);
  }
  public interface TransportationNameStep {
    CountStep transportationName(String transportationName);
  }
  

  public interface CountStep {
    DateStep count(List<Double> count);
  }
  

  public interface DateStep {
    BuildStep date(List<Temporal.Timestamp> date);
  }
  

  public interface BuildStep {
    UserTransportation build();
    BuildStep id(String id);
  }
  

  public static class Builder implements TransportationNameStep, CountStep, DateStep, BuildStep {
    private String id;
    private String transportation_name;
    private List<Double> count;
    private List<Temporal.Timestamp> date;
    @Override
     public UserTransportation build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new UserTransportation(
          id,
          transportation_name,
          count,
          date);
    }
    
    @Override
     public CountStep transportationName(String transportationName) {
        Objects.requireNonNull(transportationName);
        this.transportation_name = transportationName;
        return this;
    }
    
    @Override
     public DateStep count(List<Double> count) {
        Objects.requireNonNull(count);
        this.count = count;
        return this;
    }
    
    @Override
     public BuildStep date(List<Temporal.Timestamp> date) {
        Objects.requireNonNull(date);
        this.date = date;
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
    private CopyOfBuilder(String id, String transportationName, List<Double> count, List<Temporal.Timestamp> date) {
      super.id(id);
      super.transportationName(transportationName)
        .count(count)
        .date(date);
    }
    
    @Override
     public CopyOfBuilder transportationName(String transportationName) {
      return (CopyOfBuilder) super.transportationName(transportationName);
    }
    
    @Override
     public CopyOfBuilder count(List<Double> count) {
      return (CopyOfBuilder) super.count(count);
    }
    
    @Override
     public CopyOfBuilder date(List<Temporal.Timestamp> date) {
      return (CopyOfBuilder) super.date(date);
    }
  }
  
}
