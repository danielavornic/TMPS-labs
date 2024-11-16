package service.interfaces;

import domain.models.Book;
import domain.models.enums.BookType;

import java.util.List;

public interface IBookService {
  Book addBook(String title, String author, String isbn, int year, BookType type);

  Book checkoutBook(String isbn, String borrowerId, int loanPeriodDays);

  Book returnBook(String isbn);

  List<Book> searchBooks(String searchTerm);

  Book findBookByIsbn(String isbn);

  List<Book> getAllBooks();

  Book createBookCopy(String existingIsbn, String newIsbn, int year);
}
