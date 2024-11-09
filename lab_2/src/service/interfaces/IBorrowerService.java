package service.interfaces;

import domain.models.Book;
import domain.models.Borrower;
import java.util.List;

public interface IBorrowerService {
  Borrower addBorrower(String id, String name);

  Borrower findBorrowerById(String id);

  List<Book> getBorrowedBooks(String borrowerId);
}