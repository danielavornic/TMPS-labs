package domain.models.states;

import domain.models.BookSeries;
import domain.models.Borrower;
import domain.models.ILibraryItem;
import java.time.LocalDate;
import util.LibraryException;

public class SeriesAvailableState implements ILibraryItemState {
  private BookSeries series;

  @Override
  public void setContext(ILibraryItem item) {
    if (!(item instanceof BookSeries)) {
      throw new LibraryException("Invalid context for SeriesState");
    }
    this.series = (BookSeries) item;
  }

  @Override
  public void checkOut(Borrower borrower, int loanPeriodDays) {
    if (!series.areAllItemsAvailable()) {
      throw new LibraryException("Not all items in series are available");
    }

    series.getItems().forEach(item -> item.getState().checkOut(borrower, loanPeriodDays));

    SeriesCheckedOutState newState = new SeriesCheckedOutState();
    newState.setContext(series);
    newState.setBorrower(borrower);
    newState.setDueDate(LocalDate.now().plusDays(loanPeriodDays));
    series.setState(newState);
  }

  @Override
  public void returnItem() {
    throw new LibraryException("Cannot return an available series");
  }

  @Override
  public boolean isAvailable() {
    return true;
  }

  @Override
  public String getStatusDisplay() {
    return "Available";
  }

  @Override
  public Borrower getBorrower() {
    return null;
  }

  @Override
  public LocalDate getDueDate() {
    return null;
  }
}