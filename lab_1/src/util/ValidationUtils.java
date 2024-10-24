package util;

public class ValidationUtils {
  public static void validateIsbn(String isbn) {
    if (isbn == null || !isbn.matches("\\d{3}-\\d{10}")) {
      throw new LibraryException("Invalid ISBN format. Use XXX-XXXXXXXXXX format");
    }
  }

  public static void validateYear(int year) {
    int currentYear = java.time.Year.now().getValue();
    if (year < 1000 || year > currentYear) {
      throw new LibraryException("Invalid year. Must be between 1000 and " + currentYear);
    }
  }

  public static void validateLoanPeriod(int days) {
    if (days <= 0 || days > 30) {
      throw new LibraryException("Loan period must be between 1 and 30 days");
    }
  }

  public static void validateName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new LibraryException("Name cannot be empty");
    }
  }

  public static void validateId(String id) {
    if (id == null || !id.matches("[A-Z]\\d{3}")) {
      throw new LibraryException("Invalid ID format. Use one uppercase letter followed by 3 digits (e.g., B001)");
    }
  }
}