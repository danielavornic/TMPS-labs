package service;

import java.util.List;

import domain.database.ILibraryDatabase;
import domain.database.LibraryDatabase;
import domain.models.Book;
import domain.models.Borrower;
import util.LibraryException;
import util.ValidationUtils;

public class BorrowerService implements IBorrowerService {
  private final ILibraryDatabase database;

  public BorrowerService() {
    this.database = LibraryDatabase.getInstance();
  }

  public Borrower addBorrower(String id, String name) {
    ValidationUtils.validateId(id);
    ValidationUtils.validateName(name);

    if (database.findBorrowerById(id) != null) {
      throw new LibraryException("Borrower ID already exists");
    }

    Borrower borrower = new Borrower(id, name);
    database.addBorrower(borrower);
    return borrower;
  }

  public Borrower findBorrowerById(String id) {
    ValidationUtils.validateId(id);
    return database.findBorrowerById(id);
  }

  public List<Book> getBorrowedBooks(String borrowerId) {
    Borrower borrower = findBorrowerById(borrowerId);
    if (borrower == null) {
      throw new LibraryException("Borrower not found");
    }
    return borrower.getBorrowedBooks();
  }
}