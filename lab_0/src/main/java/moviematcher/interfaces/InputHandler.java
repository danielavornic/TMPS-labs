package moviematcher.interfaces;

public interface InputHandler {
  String getMood();

  String getSituation();

  int getPostRecommendationChoice();

  void displayRecommendation(String mood, String situation, String movie);

  void close();
}