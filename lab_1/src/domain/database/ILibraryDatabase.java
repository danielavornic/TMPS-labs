package domain.database;

import domain.models.Book;
import domain.models.Borrower;
import java.util.List;

public interface ILibraryDatabase {
  void addBook(Book book);

  void addBorrower(Borrower borrower);

  Book findBookByIsbn(String isbn);

  Borrower findBorrowerById(String id);

  List<Book> searchBooks(String searchTerm);

  List<Book> getAllBooks();
}