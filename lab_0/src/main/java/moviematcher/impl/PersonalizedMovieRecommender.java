package moviematcher.impl;

import java.util.*;

import moviematcher.config.MovieMatcherConfig;
import moviematcher.interfaces.MovieRecommender;
import moviematcher.interfaces.RecommendationStorage;
import moviematcher.model.Mood;
import moviematcher.model.Situation;

public class PersonalizedMovieRecommender implements MovieRecommender {
  private final RecommendationStorage storage;
  private final Random random = new Random();

  public PersonalizedMovieRecommender(RecommendationStorage storage) {
    this.storage = storage;
    initializeRecommendations();
  }

  private void initializeRecommendations() {
    Mood[] moods = Mood.values();
    Situation[] situations = Situation.values();

    String[][][] recommendations = {
        {
            { "The Secret Life of Walter Mitty", "Amélie", "Little Miss Sunshine", "Singing in the Rain" },
            { "Inside Out", "The Pursuit of Happyness", "Big Fish", "Life Is Beautiful" },
            { "The Avengers", "Guardians of the Galaxy", "The Incredibles", "The Matrix" },
            { "Black Swan", "The Sixth Sense", "The Others", "The Shining" },
            { "The Grand Budapest Hotel", "Midnight in Paris", "The Big Lebowski", "Lost in Translation" }
        },
        {
            { "The Best Exotic Marigold Hotel", "Mamma Mia!", "Roman Holiday", "The Talented Mr. Ripley" },
            { "Eat Pray Love", "Under the Tuscan Sun", "Lost in Translation", "The Secret Life of Walter Mitty" },
            { "Indiana Jones and the Last Crusade", "Jurassic Park", "The Mummy", "National Treasure" },
            { "The Blair Witch Project", "The Descent", "The Cabin in the Woods", "The Strangers" },
            { "The Beach", "Mamma Mia!", "The Descendants", "The Secret Life of Walter Mitty" }
        },
        {
            { "The Theory of Everything", "Good Will Hunting", "October Sky", "The Imitation Game" },
            { "Dead Poets Society", "The Breakfast Club", "Stand and Deliver", "Freedom Writers" },
            { "The Social Network", "The Imitation Game", "The Founder", "Moneyball" },
            { "The Social Network", "The Imitation Game", "The Founder", "Moneyball" },
            { "The Theory of Everything", "Good Will Hunting", "October Sky", "The Imitation Game" }
        },
        {
            { "The Hangover", "Bridesmaids", "Superbad", "Girls Trip" },
            { "The Perks of Being a Wallflower", "St. Elmo's Fire", "Steel Magnolias", "Beaches" },
            { "The Hangover", "Superbad", "21 Jump Street", "Project X" },
            { "The Hangover", "Superbad", "21 Jump Street", "Project X" },
            { "The Hangover", "Bridesmaids", "Superbad", "Girls Trip" }
        },
        {
            { MovieMatcherConfig.DEFAULT_MOVIE },
            { MovieMatcherConfig.DEFAULT_MOVIE },
            { MovieMatcherConfig.DEFAULT_MOVIE },
            { MovieMatcherConfig.DEFAULT_MOVIE },
            { MovieMatcherConfig.DEFAULT_MOVIE }
        }
    };

    for (int i = 0; i < moods.length; i++) {
      for (int j = 0; j < situations.length; j++) {
        storage.addRecommendation(moods[i].getDisplayName(), situations[j].getDisplayName(),
            Arrays.asList(recommendations[j][i]));
      }
      storage.addRecommendation(moods[i].getDisplayName(), "default",
          Arrays.asList(recommendations[situations.length][i]));
    }
  }

  @Override
  public String recommendMovie(String mood, String situation) {
    List<String> recommendations = storage.getRecommendation(mood, situation);
    if (recommendations.isEmpty()) {
      recommendations = storage.getRecommendation(mood, "default");
    }
    return recommendations.isEmpty() ? MovieMatcherConfig.DEFAULT_MOVIE
        : recommendations.get(random.nextInt(recommendations.size()));
  }
}
