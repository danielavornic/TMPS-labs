package domain.models.decorators;

import domain.models.Book;
import domain.models.enums.BookType;

public abstract class BookDecorator extends Book {
  protected Book book;

  public BookDecorator(Book book) {
    super(book.getTitle(), book.getAuthor(), book.getIsbn(), book.getYear());
    this.book = book;
  }

  @Override
  public BookType getType() {
    return book.getType();
  }

  @Override
  public double calculateLateFee(long daysLate) {
    return book.calculateLateFee(daysLate);
  }

  @Override
  public int getMaxLoanDays() {
    return book.getMaxLoanDays();
  }
}