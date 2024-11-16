package domain.models.states;

import domain.models.Book;
import domain.models.Borrower;
import domain.models.ILibraryItem;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import util.LibraryException;

public class BookCheckedOutState implements ILibraryItemState {
  private Book book;
  private Borrower borrower;
  private LocalDate dueDate;

  @Override
  public void setContext(ILibraryItem item) {
    if (!(item instanceof Book)) {
      throw new LibraryException("Invalid context for BookState");
    }
    this.book = (Book) item;
  }

  public void setBorrower(Borrower borrower) {
    this.borrower = borrower;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  @Override
  public void checkOut(Borrower borrower, int loanPeriodDays) {
    throw new LibraryException("Book is already checked out");
  }

  @Override
  public void returnItem() {
    BookAvailableState newState = new BookAvailableState();
    newState.setContext(book);
    book.setState(newState);
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
}
