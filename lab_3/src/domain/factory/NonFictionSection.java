package domain.factory;

import domain.models.Book;
import domain.models.NonFictionBook;

public class NonFictionSection extends BookCreator {
  @Override
  public Book createBook(String title, String author, String isbn, int year) {
    return new NonFictionBook(title, author, isbn, year);
  }
}