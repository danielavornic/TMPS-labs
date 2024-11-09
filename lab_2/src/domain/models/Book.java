package domain.models;

import java.time.LocalDate;

import domain.models.enums.BookType;
import util.LibraryException;

public abstract class Book implements ILibraryItem, Cloneable {
  private String title;
  private String author;
  private String isbn;
  private int year;
  private boolean checkedOut;
  private LocalDate dueDate;
  private Borrower borrower;

  protected Book(String title, String author, String isbn, int year) {
    this.title = title;
    this.author = author;
    this.isbn = isbn;
    this.year = year;
    this.checkedOut = false;
  }

  @Override
  public Book clone() {
    try {
      Book clonedBook = (Book) super.clone();
      clonedBook.checkedOut = false;
      clonedBook.dueDate = null;
      clonedBook.borrower = null;
      return clonedBook;
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException("Error cloning book", e);
    }
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public String getIsbn() {
    return isbn;
  }

  public int getYear() {
    return year;
  }

  public boolean isCheckedOut() {
    return checkedOut;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public Borrower getBorrower() {
    return borrower;
  }

  public String setISBN(String isbn) {
    return this.isbn = isbn;
  }

  public int setYear(int year) {
    return this.year = year;
  }

  public void checkOut(Borrower borrower, int loanPeriodDays) {
    if (loanPeriodDays > getMaxLoanDays()) {
      throw new LibraryException("Maximum loan period exceeded for this book type");
    }
    this.checkedOut = true;
    this.borrower = borrower;
    this.dueDate = LocalDate.now().plusDays(loanPeriodDays);
  }

  public void returnBook() {
    this.checkedOut = false;
    this.borrower = null;
    this.dueDate = null;
  }

  @Override
  public String toString() {
    return "Book{" +
        "title='" + title + '\'' +
        ", author='" + author + '\'' +
        ", isbn='" + isbn + '\'' +
        ", year=" + year +
        ", genre= + " + getType() +
        ", checkedOut=" + checkedOut +
        ", dueDate=" + dueDate +
        ", borrower=" + (borrower != null ? borrower.getName() : "none") +
        '}';
  }

  public abstract BookType getType();

  public abstract int getMaxLoanDays();

  public abstract double calculateLateFee(long daysLate);

  @Override
  public boolean isAvailable() {
    return !isCheckedOut();
  }

  @Override
  public void returnItem() {
    returnBook();
  }

  @Override
  public String getDisplayInfo() {
    StringBuilder info = new StringBuilder();
    info.append(String.format("Title: %s\n", getTitle()));
    info.append(String.format("Author: %s\n", getAuthor()));
    info.append(String.format("ISBN: %s\n", getIsbn()));
    info.append(String.format("Year: %d\n", getYear()));
    info.append(String.format("Type: %s\n", getType()));
    info.append(String.format("Status: %s", isCheckedOut() ? "Checked Out" : "Available"));
    return info.toString();
  }

}