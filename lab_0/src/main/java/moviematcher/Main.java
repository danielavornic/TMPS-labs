package moviematcher;

import moviematcher.impl.*;
import moviematcher.interfaces.*;

public class Main {
  public static void main(String[] args) {
    RecommendationStorage storage = new HashMapRecommendationStorage();
    MovieRecommender recommender = new PersonalizedMovieRecommender(storage);
    InputHandler inputHandler = new ConsoleInputHandler();

    String mood = null;
    String situation = null;

    while (true) {
      if (mood == null || situation == null) {
        mood = inputHandler.getMood();
        if (mood.equals("quit"))
          break;

        situation = inputHandler.getSituation();
        if (situation.equals("quit"))
          break;
      }

      String recommendedMovie = recommender.recommendMovie(mood, situation);
      inputHandler.displayRecommendation(mood, situation, recommendedMovie);

      int choice = inputHandler.getPostRecommendationChoice();
      if (choice == 1) {
        continue;
      } else if (choice == 2) {
        mood = null;
        situation = null;
      } else {
        break;
      }
    }

    inputHandler.close();
  }
}