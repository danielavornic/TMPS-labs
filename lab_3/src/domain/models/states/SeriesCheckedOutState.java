package domain.models.states;

import domain.models.BookSeries;
import domain.models.Borrower;
import domain.models.ILibraryItem;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import util.LibraryException;

public class SeriesCheckedOutState implements ILibraryItemState {
  private BookSeries series;
  private Borrower borrower;
  private LocalDate dueDate;

  @Override
  public void setContext(ILibraryItem item) {
    if (!(item instanceof BookSeries)) {
      throw new LibraryException("Invalid context for SeriesState");
    }
    this.series = (BookSeries) item;
  }

  public void setBorrower(Borrower borrower) {
    this.borrower = borrower;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  @Override
  public void checkOut(Borrower borrower, double loanPeriodDays) {
    throw new LibraryException("Series is already checked out");
  }

  @Override
  public void returnItem() {
    // Return all items in the series
    series.getItems().forEach(item -> item.getState().returnItem());

    // Then transition to available state
    SeriesAvailableState newState = new SeriesAvailableState();
    newState.setContext(series);
    series.setState(newState);

    borrower.update(String.format(
        "You have returned '%s'. Thank you!",
        series.getTitle()));
  }

  @Override
  public boolean isAvailable() {
    return false;
  }

  @Override
  public String getStatusDisplay() {
    return String.format("Checked out to %s until %s",
        borrower.getName(),
        dueDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
  }

  @Override
  public Borrower getBorrower() {
    return borrower;
  }

  @Override
  public LocalDate getDueDate() {
    return dueDate;
  }

  @Override
  public void checkDueDate() {
    LocalDate today = LocalDate.now();
    if (dueDate != null) {
      long daysUntilDue = ChronoUnit.DAYS.between(today, dueDate);

      if (daysUntilDue == 2) {
        borrower.update(String.format(
            "REMINDER: Book series '%s' is due in 2 days (Due: %s)",
            series.getTitle(),
            dueDate.format(DateTimeFormatter.ISO_LOCAL_DATE)));
      } else if (daysUntilDue <= 0) {
        borrower.update(String.format(
            "OVERDUE: Book series '%s' was due on %s. Please return it as soon as possible.",
            series.getTitle(),
            dueDate.format(DateTimeFormatter.ISO_LOCAL_DATE)));
      }
    }
  }
}