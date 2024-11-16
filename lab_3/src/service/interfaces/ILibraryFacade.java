package service.interfaces;

import domain.models.*;
import domain.models.enums.BookFormat;
import domain.models.enums.BookType;

import java.util.List;

public interface ILibraryFacade {
  Book addBook(String title, String author, String isbn, int year,
      BookType type, BookFormat format);

  Book findBookByIsbn(String isbn);

  List<Book> searchBooks(String searchTerm);

  List<Book> getAllBooks();

  Book createBookCopy(String existingIsbn, String newIsbn, int year);

  BookSeries createSeries(String title);

  void addBookToSeries(String seriesTitle, String isbn);

  List<BookSeries> getAllSeries();

  BookSeries findSeriesByTitle(String title);

  Borrower addBorrower(String id, String name);

  ILibraryItem checkoutItem(String itemId, String borrowerId, double days);

  ILibraryItem returnItem(String itemId);

  List<String> getBorrowerNotifications(String borrowerId);

  void clearBorrowerNotifications(String borrowerId);

  void checkAllDueDates();
}