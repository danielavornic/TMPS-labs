package moviematcher.impl;

import java.util.*;

import moviematcher.interfaces.RecommendationStorage;

public class HashMapRecommendationStorage implements RecommendationStorage {
  private final Map<String, Map<String, List<String>>> storage = new HashMap<>();

  @Override
  public void addRecommendation(String mood, String situation, List<String> movies) {
    storage.computeIfAbsent(mood, k -> new HashMap<>()).put(situation, movies);
  }

  @Override
  public List<String> getRecommendation(String mood, String situation) {
    return storage.getOrDefault(mood, Collections.emptyMap())
        .getOrDefault(situation, Collections.emptyList());
  }
}