package domain.models.states;

import domain.models.Book;
import domain.models.Borrower;
import domain.models.ILibraryItem;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
  public void checkOut(Borrower borrower, double loanPeriodDays) {
    throw new LibraryException("Book is already checked out");
  }

  @Override
  public void returnItem() {
    BookAvailableState newState = new BookAvailableState();
    newState.setContext(book);
    book.setState(newState);

    borrower.update(String.format(
        "You have returned '%s'. Thank you!",
        book.getTitle()));
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
            "REMINDER: Book '%s' is due in 2 days (Due: %s)",
            book.getTitle(),
            dueDate.format(DateTimeFormatter.ISO_LOCAL_DATE)));

        // For testing with double loan period, leave the <= 0 condition
      } else if (daysUntilDue <= 0) {
        borrower.update(String.format(
            "OVERDUE: Book '%s' was due on %s. Please return it as soon as possible.",
            book.getTitle(),
            dueDate.format(DateTimeFormatter.ISO_LOCAL_DATE)));
      }
    }
  }
}