package moviematcher.interfaces;

import java.util.List;

public interface RecommendationStorage {
  void addRecommendation(String mood, String situation, List<String> movies);

  List<String> getRecommendation(String mood, String situation);
}