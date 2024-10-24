package domain.factory;

import domain.models.Book;
import domain.models.FictionBook;

public class FictionSection extends BookCreator {
  @Override
  public Book createBook(String title, String author, String isbn, int year) {
    return new FictionBook(title, author, isbn, year);
  }
}
