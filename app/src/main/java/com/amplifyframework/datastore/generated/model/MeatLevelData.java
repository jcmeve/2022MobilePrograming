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

/** This is an auto generated class representing the MeatLevelData type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "MeatLevelData", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class MeatLevelData implements Model {
  public static final QueryField ID = field("MeatLevelData", "id");
  public static final QueryField HIGH_CARBON = field("MeatLevelData", "high_carbon");
  public static final QueryField LOW_CARBON = field("MeatLevelData", "low_carbon");
  public static final QueryField NO_CARBON = field("MeatLevelData", "no_carbon");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Int") Integer high_carbon;
  private final @ModelField(targetType="Int") Integer low_carbon;
  private final @ModelField(targetType="Int") Integer no_carbon;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public Integer getHighCarbon() {
      return high_carbon;
  }
  
  public Integer getLowCarbon() {
      return low_carbon;
  }
  
  public Integer getNoCarbon() {
      return no_carbon;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private MeatLevelData(String id, Integer high_carbon, Integer low_carbon, Integer no_carbon) {
    this.id = id;
    this.high_carbon = high_carbon;
    this.low_carbon = low_carbon;
    this.no_carbon = no_carbon;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      MeatLevelData meatLevelData = (MeatLevelData) obj;
      return ObjectsCompat.equals(getId(), meatLevelData.getId()) &&
              ObjectsCompat.equals(getHighCarbon(), meatLevelData.getHighCarbon()) &&
              ObjectsCompat.equals(getLowCarbon(), meatLevelData.getLowCarbon()) &&
              ObjectsCompat.equals(getNoCarbon(), meatLevelData.getNoCarbon()) &&
              ObjectsCompat.equals(getCreatedAt(), meatLevelData.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), meatLevelData.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getHighCarbon())
      .append(getLowCarbon())
      .append(getNoCarbon())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("MeatLevelData {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("high_carbon=" + String.valueOf(getHighCarbon()) + ", ")
      .append("low_carbon=" + String.valueOf(getLowCarbon()) + ", ")
      .append("no_carbon=" + String.valueOf(getNoCarbon()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
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
  public static MeatLevelData justId(String id) {
    return new MeatLevelData(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      high_carbon,
      low_carbon,
      no_carbon);
  }
  public interface BuildStep {
    MeatLevelData build();
    BuildStep id(String id);
    BuildStep highCarbon(Integer highCarbon);
    BuildStep lowCarbon(Integer lowCarbon);
    BuildStep noCarbon(Integer noCarbon);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private Integer high_carbon;
    private Integer low_carbon;
    private Integer no_carbon;
    @Override
     public MeatLevelData build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new MeatLevelData(
          id,
          high_carbon,
          low_carbon,
          no_carbon);
    }
    
    @Override
     public BuildStep highCarbon(Integer highCarbon) {
        this.high_carbon = highCarbon;
        return this;
    }
    
    @Override
     public BuildStep lowCarbon(Integer lowCarbon) {
        this.low_carbon = lowCarbon;
        return this;
    }
    
    @Override
     public BuildStep noCarbon(Integer noCarbon) {
        this.no_carbon = noCarbon;
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
    private CopyOfBuilder(String id, Integer highCarbon, Integer lowCarbon, Integer noCarbon) {
      super.id(id);
      super.highCarbon(highCarbon)
        .lowCarbon(lowCarbon)
        .noCarbon(noCarbon);
    }
    
    @Override
     public CopyOfBuilder highCarbon(Integer highCarbon) {
      return (CopyOfBuilder) super.highCarbon(highCarbon);
    }
    
    @Override
     public CopyOfBuilder lowCarbon(Integer lowCarbon) {
      return (CopyOfBuilder) super.lowCarbon(lowCarbon);
    }
    
    @Override
     public CopyOfBuilder noCarbon(Integer noCarbon) {
      return (CopyOfBuilder) super.noCarbon(noCarbon);
    }
  }
  
}
