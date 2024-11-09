package domain.database;

import java.util.List;

import domain.models.*;

public interface ILibraryDatabase {
  void addBook(Book book);

  void addBorrower(Borrower borrower);

  void addSeries(BookSeries series);

  Book findBookByIsbn(String isbn);

  BookSeries findSeriesByTitle(String title);

  List<BookSeries> getAllSeries();

  Borrower findBorrowerById(String id);

  List<Book> searchBooks(String searchTerm);

  List<Book> getAllBooks();

}
