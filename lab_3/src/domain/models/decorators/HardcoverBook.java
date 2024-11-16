package domain.models.decorators;

import domain.models.*;

public class HardcoverBook extends BookDecorator {
  private static final double LATE_FEE_MULTIPLIER = 1.5;
  private static final int ADDITIONAL_LOAN_DAYS = 7;

  public HardcoverBook(Book book) {
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