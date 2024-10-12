package moviematcher.model;

public enum Situation {
  AT_HOME("at home"),
  ON_VACATION("on vacation"),
  STUDYING("studying"),
  WITH_FRIENDS("with friends");

  private final String displayName;

  Situation(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
