package domain.models.states;

import domain.models.Book;
import domain.models.Borrower;
import domain.models.ILibraryItem;
import java.time.LocalDate;
import util.LibraryException;

public class BookAvailableState implements ILibraryItemState {
  private Book book;

  @Override
  public void setContext(ILibraryItem item) {
    if (!(item instanceof Book)) {
      throw new LibraryException("Invalid context for BookState");
    }
    this.book = (Book) item;
  }

  @Override
  public void checkOut(Borrower borrower, int loanPeriodDays) {
    if (loanPeriodDays > book.getMaxLoanDays()) {
      throw new LibraryException("Maximum loan period exceeded for this book type");
    }

    BookCheckedOutState newState = new BookCheckedOutState();
    newState.setContext(book);
    newState.setBorrower(borrower);
    newState.setDueDate(LocalDate.now().plusDays(loanPeriodDays));
    book.setState(newState);
  }

  @Override
  public void returnItem() {
    throw new LibraryException("Cannot return an available book");
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