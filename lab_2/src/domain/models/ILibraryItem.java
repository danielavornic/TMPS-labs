package domain.models;

public interface ILibraryItem {
  String getTitle();

  boolean isAvailable();

  void checkOut(Borrower borrower, int loanPeriodDays);

  void returnItem();

  double calculateLateFee(long daysLate);

  String getDisplayInfo();
}