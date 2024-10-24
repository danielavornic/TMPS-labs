package domain.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import domain.models.Book;
import domain.models.Borrower;

public class LibraryDatabase implements ILibraryDatabase {
  private static volatile ILibraryDatabase instance;
  private final List<Book> books;
  private final List<Borrower> borrowers;

  private LibraryDatabase() {
    books = Collections.synchronizedList(new ArrayList<>());
    borrowers = Collections.synchronizedList(new ArrayList<>());
  }

  public static ILibraryDatabase getInstance() {
    ILibraryDatabase result = instance;
    if (result != null) {
      return result;
    }
    synchronized (LibraryDatabase.class) {
      if (instance == null) {
        instance = new LibraryDatabase();
      }
      return instance;
    }
  }

  public synchronized void addBook(Book book) {
    books.add(book);
  }

  public synchronized void addBorrower(Borrower borrower) {
    borrowers.add(borrower);
  }

  public Book findBookByIsbn(String isbn) {
    synchronized (books) {
      return books.stream()
          .filter(book -> book.getIsbn().equals(isbn))
          .findFirst()
          .orElse(null);
    }
  }

  public List<Book> searchBooks(String searchTerm) {
    synchronized (books) {
      return books.stream()
          .filter(book -> book.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
              book.getAuthor().toLowerCase().contains(searchTerm.toLowerCase()) ||
              book.getIsbn().contains(searchTerm))
          .collect(Collectors.toList());
    }
  }

  public Borrower findBorrowerById(String id) {
    synchronized (borrowers) {
      return borrowers.stream()
          .filter(b -> b.getId().equals(id))
          .findFirst()
          .orElse(null);
    }
  }

  public List<Book> getAllBooks() {
    synchronized (books) {
      return new ArrayList<>(books);
    }
  }
}