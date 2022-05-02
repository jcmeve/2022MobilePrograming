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

/** This is an auto generated class representing the FoodData type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "FoodData", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "undefined", fields = {"id"})
public final class FoodData implements Model {
  public static final QueryField ID = field("FoodData", "id");
  public static final QueryField NAME = field("FoodData", "name");
  public static final QueryField UNIT = field("FoodData", "unit");
  public static final QueryField CARBON_PER_GRAM = field("FoodData", "carbon_per_gram");
  public static final QueryField GRAM_PER_UNIT = field("FoodData", "gram_per_unit");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String", isRequired = true) String unit;
  private final @ModelField(targetType="Int", isRequired = true) Integer carbon_per_gram;
  private final @ModelField(targetType="Int", isRequired = true) Integer gram_per_unit;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getUnit() {
      return unit;
  }
  
  public Integer getCarbonPerGram() {
      return carbon_per_gram;
  }
  
  public Integer getGramPerUnit() {
      return gram_per_unit;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private FoodData(String id, String name, String unit, Integer carbon_per_gram, Integer gram_per_unit) {
    this.id = id;
    this.name = name;
    this.unit = unit;
    this.carbon_per_gram = carbon_per_gram;
    this.gram_per_unit = gram_per_unit;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      FoodData foodData = (FoodData) obj;
      return ObjectsCompat.equals(getId(), foodData.getId()) &&
              ObjectsCompat.equals(getName(), foodData.getName()) &&
              ObjectsCompat.equals(getUnit(), foodData.getUnit()) &&
              ObjectsCompat.equals(getCarbonPerGram(), foodData.getCarbonPerGram()) &&
              ObjectsCompat.equals(getGramPerUnit(), foodData.getGramPerUnit()) &&
              ObjectsCompat.equals(getCreatedAt(), foodData.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), foodData.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getUnit())
      .append(getCarbonPerGram())
      .append(getGramPerUnit())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("FoodData {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("unit=" + String.valueOf(getUnit()) + ", ")
      .append("carbon_per_gram=" + String.valueOf(getCarbonPerGram()) + ", ")
      .append("gram_per_unit=" + String.valueOf(getGramPerUnit()) + ", ")
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
  public static FoodData justId(String id) {
    return new FoodData(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      unit,
      carbon_per_gram,
      gram_per_unit);
  }
  public interface NameStep {
    UnitStep name(String name);
  }
  

  public interface UnitStep {
    CarbonPerGramStep unit(String unit);
  }
  

  public interface CarbonPerGramStep {
    GramPerUnitStep carbonPerGram(Integer carbonPerGram);
  }
  

  public interface GramPerUnitStep {
    BuildStep gramPerUnit(Integer gramPerUnit);
  }
  

  public interface BuildStep {
    FoodData build();
    BuildStep id(String id);
  }
  

  public static class Builder implements NameStep, UnitStep, CarbonPerGramStep, GramPerUnitStep, BuildStep {
    private String id;
    private String name;
    private String unit;
    private Integer carbon_per_gram;
    private Integer gram_per_unit;
    @Override
     public FoodData build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new FoodData(
          id,
          name,
          unit,
          carbon_per_gram,
          gram_per_unit);
    }
    
    @Override
     public UnitStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public CarbonPerGramStep unit(String unit) {
        Objects.requireNonNull(unit);
        this.unit = unit;
        return this;
    }
    
    @Override
     public GramPerUnitStep carbonPerGram(Integer carbonPerGram) {
        Objects.requireNonNull(carbonPerGram);
        this.carbon_per_gram = carbonPerGram;
        return this;
    }
    
    @Override
     public BuildStep gramPerUnit(Integer gramPerUnit) {
        Objects.requireNonNull(gramPerUnit);
        this.gram_per_unit = gramPerUnit;
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
    private CopyOfBuilder(String id, String name, String unit, Integer carbonPerGram, Integer gramPerUnit) {
      super.id(id);
      super.name(name)
        .unit(unit)
        .carbonPerGram(carbonPerGram)
        .gramPerUnit(gramPerUnit);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder unit(String unit) {
      return (CopyOfBuilder) super.unit(unit);
    }
    
    @Override
     public CopyOfBuilder carbonPerGram(Integer carbonPerGram) {
      return (CopyOfBuilder) super.carbonPerGram(carbonPerGram);
    }
    
    @Override
     public CopyOfBuilder gramPerUnit(Integer gramPerUnit) {
      return (CopyOfBuilder) super.gramPerUnit(gramPerUnit);
    }
  }
  
}
