package com.amplifyframework.datastore.generated.model;


import androidx.core.util.ObjectsCompat;

import java.util.Objects;
import java.util.List;

/** This is an auto generated class representing the GameScore type in your schema. */
public final class GameScore {
  private final List<Integer> score;
  public List<Integer> getScore() {
      return score;
  }
  
  private GameScore(List<Integer> score) {
    this.score = score;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      GameScore gameScore = (GameScore) obj;
      return ObjectsCompat.equals(getScore(), gameScore.getScore());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getScore())
      .toString()
      .hashCode();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(score);
  }
  public interface ScoreStep {
    BuildStep score(List<Integer> score);
  }
  

  public interface BuildStep {
    GameScore build();
  }
  

  public static class Builder implements ScoreStep, BuildStep {
    private List<Integer> score;
    @Override
     public GameScore build() {
        
        return new GameScore(
          score);
    }
    
    @Override
     public BuildStep score(List<Integer> score) {
        Objects.requireNonNull(score);
        this.score = score;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(List<Integer> score) {
      super.score(score);
    }
    
    @Override
     public CopyOfBuilder score(List<Integer> score) {
      return (CopyOfBuilder) super.score(score);
    }
  }
  
}
