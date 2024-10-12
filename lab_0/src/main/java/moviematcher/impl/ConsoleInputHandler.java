package moviematcher.impl;

import moviematcher.interfaces.InputHandler;
import moviematcher.config.MovieMatcherConfig;
import java.util.*;

public class ConsoleInputHandler implements InputHandler {
  private final Scanner scanner;

  public ConsoleInputHandler() {
    this.scanner = new Scanner(System.in);
  }

  @Override
  public String getMood() {
    while (true) {
      System.out.println("Enter the number for your mood or 0 to quit:");
      for (int i = 0; i < MovieMatcherConfig.VALID_MOODS.size(); i++) {
        System.out.println((i + 1) + ". " + MovieMatcherConfig.VALID_MOODS.get(i).getDisplayName());
      }

      try {
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice == 0) {
          return "quit";
        } else if (choice > 0 && choice <= MovieMatcherConfig.VALID_MOODS.size()) {
          return MovieMatcherConfig.VALID_MOODS.get(choice - 1).getDisplayName();
        } else {
          System.out.println("Invalid choice. Please try again.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Please enter a valid number.");
      }
    }
  }

  @Override
  public String getSituation() {
    while (true) {
      System.out.println("Enter the number for your situation or 0 to quit:");
      for (int i = 0; i < MovieMatcherConfig.VALID_SITUATIONS.size(); i++) {
        System.out.println((i + 1) + ". " + MovieMatcherConfig.VALID_SITUATIONS.get(i).getDisplayName());
      }

      try {
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice == 0) {
          return "quit";
        } else if (choice > 0 && choice <= MovieMatcherConfig.VALID_SITUATIONS.size()) {
          return MovieMatcherConfig.VALID_SITUATIONS.get(choice - 1).getDisplayName();
        } else {
          System.out.println("Invalid choice. Please try again.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Please enter a valid number.");
      }
    }
  }

  @Override
  public int getPostRecommendationChoice() {
    while (true) {
      System.out.println("What would you like to do next?");
      for (int i = 0; i < MovieMatcherConfig.POST_RECOMMENDATION_OPTIONS.size(); i++) {
        System.out.println((i + 1) + ". " + MovieMatcherConfig.POST_RECOMMENDATION_OPTIONS.get(i));
      }

      try {
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice >= 1 && choice <= MovieMatcherConfig.POST_RECOMMENDATION_OPTIONS.size()) {
          return choice;
        } else {
          System.out.println("Invalid choice. Please enter a valid number.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Please enter a valid number.");
      }
    }
  }

  @Override
  public void displayRecommendation(String mood, String situation, String movie) {
    System.out.println("Based on your " + mood + " mood while " + situation + ", we recommend: " + movie);
    System.out.println();
  }

  @Override
  public void close() {
    scanner.close();
  }
}