package domain.factory;

import domain.models.*;

public abstract class BookCreator {
  public abstract Book createBook(String title, String author, String isbn, int year);
}