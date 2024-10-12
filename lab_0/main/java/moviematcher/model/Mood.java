package moviematcher.model;

public enum Mood {
  HAPPY("happy"),
  SAD("sad"),
  EXCITED("excited"),
  ANXIOUS("anxious"),
  RELAXED("relaxed");

  private final String displayName;

  Mood(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
