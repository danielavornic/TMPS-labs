package domain.models.states;

import domain.models.ILibraryItem;
import domain.models.Borrower;
import java.time.LocalDate;

public interface ILibraryItemState {
  void setContext(ILibraryItem item);

  void checkOut(Borrower borrower, int loanPeriodDays);

  void returnItem();

  boolean isAvailable();

  String getStatusDisplay();

  Borrower getBorrower();

  LocalDate getDueDate();
}