package domain.models;

import domain.models.enums.BookType;
import domain.models.states.BookAvailableState;
import domain.models.states.ILibraryItemState;

public abstract class Book implements ILibraryItem, Cloneable {
  private String title;
  private String author;
  private String isbn;
  private int year;
  private ILibraryItemState state;

  protected Book(String title, String author, String isbn, int year) {
    this.title = title;
    this.author = author;
    this.isbn = isbn;
    this.year = year;
    this.state = new BookAvailableState();
    this.state.setContext(this);
  }

  @Override
  public Book clone() {
    try {
      Book clonedBook = (Book) super.clone();
      clonedBook.state = new BookAvailableState();
      clonedBook.state.setContext(clonedBook);
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

  public String setISBN(String isbn) {
    return this.isbn = isbn;
  }

  public int setYear(int year) {
    return this.year = year;
  }

  @Override
  public void setState(ILibraryItemState state) {
    this.state = state;
    state.setContext(this);
  }

  @Override
  public ILibraryItemState getState() {
    return state;
  }

  public abstract BookType getType();

  public abstract int getMaxLoanDays();

  public abstract double calculateLateFee(long daysLate);

  @Override
  public String toString() {
    return "Book{" +
        "title='" + title + '\'' +
        ", author='" + author + '\'' +
        ", isbn='" + isbn + '\'' +
        ", year=" + year +
        ", type=" + getType() +
        ", status=" + state.getStatusDisplay() +
        '}';
  }

  @Override
  public String getDisplayInfo() {
    StringBuilder info = new StringBuilder();
    info.append(String.format("Title: %s\n", getTitle()));
    info.append(String.format("Author: %s\n", getAuthor()));
    info.append(String.format("ISBN: %s\n", getIsbn()));
    info.append(String.format("Year: %d\n", getYear()));
    info.append(String.format("Type: %s\n", getType()));
    info.append(String.format("Status: %s", state.getStatusDisplay()));
    return info.toString();
  }
}