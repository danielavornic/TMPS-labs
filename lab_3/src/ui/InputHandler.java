package ui;

import util.LibraryException;
import java.util.Scanner;

public class InputHandler {
  private static final Scanner scanner = new Scanner(System.in);

  public static String readString(String prompt) {
    System.out.print(prompt);
    String input = scanner.nextLine().trim();
    if (input.isEmpty()) {
      throw new LibraryException("Input cannot be empty");
    }
    return input;
  }

  public static void pause() {
    System.out.println("\nPress Enter to continue...");
    scanner.nextLine();
  }

  public static int readInt(String prompt, int min, int max) {
    System.out.print(prompt);
    try {
      int value = Integer.parseInt(scanner.nextLine().trim());
      if (value < min || value > max) {
        throw new LibraryException(
            String.format("Value must be between %d and %d", min, max));
      }
      return value;
    } catch (NumberFormatException e) {
      throw new LibraryException("Please enter a valid number");
    }
  }

  public static double readDouble(String prompt, double min, double max) {
    System.out.print(prompt);
    try {
      double value = Double.parseDouble(scanner.nextLine().trim());
      if (value < min || value > max) {
        throw new LibraryException(
            String.format("Value must be between %.2f and %.2f", min, max));
      }
      return value;
    } catch (NumberFormatException e) {
      throw new LibraryException("Please enter a valid number");
    }
  }

  public static String readIsbn(String prompt) {
    System.out.print(prompt);
    String isbn = scanner.nextLine().trim();
    if (!isbn.matches("\\d{3}-\\d{10}")) {
      throw new LibraryException("Invalid ISBN format. Use XXX-XXXXXXXXXX format");
    }
    return isbn;
  }

  public static String readBorrowerId(String prompt) {
    System.out.print(prompt);
    String id = scanner.nextLine().trim();
    if (!id.matches("[A-Z]\\d{3}")) {
      throw new LibraryException("Invalid ID format. Use one uppercase letter followed by 3 digits (e.g., B001)");
    }
    return id;
  }
}