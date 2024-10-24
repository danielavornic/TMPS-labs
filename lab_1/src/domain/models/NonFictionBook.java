package domain.models;

import domain.factory.BookType;

public class NonFictionBook extends Book {
  private static final int MAX_LOAN_DAYS = 14;
  private static final double LATE_FEE_PER_DAY = 0.75;

  public NonFictionBook(String title, String author, String isbn, int year) {
    super(title, author, isbn, year);
  }

  @Override
  public BookType getType() {
    return BookType.NON_FICTION;
  }

  @Override
  public int getMaxLoanDays() {
    return MAX_LOAN_DAYS;
  }

  @Override
  public double calculateLateFee(long daysLate) {
    return LATE_FEE_PER_DAY * daysLate;
  }
}