package domain.models.decorators;

import domain.models.*;

public class PaperbackBook extends BookDecorator {
  private static final double LATE_FEE_MULTIPLIER = 1.2;
  private static final int ADDITIONAL_LOAN_DAYS = 3;

  public PaperbackBook(Book book) {
    super(book);
  }

  @Override
  public double calculateLateFee(long daysLate) {
    return super.calculateLateFee(daysLate) * LATE_FEE_MULTIPLIER;
  }

  @Override
  public int getMaxLoanDays() {
    return super.getMaxLoanDays() + ADDITIONAL_LOAN_DAYS;
  }
}