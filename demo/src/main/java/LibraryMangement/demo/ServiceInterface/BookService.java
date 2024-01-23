package LibraryMangement.demo.ServiceInterface;

import LibraryMangement.demo.Entity.Book;
import LibraryMangement.demo.Entity.BorrowingRecord;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Set;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book addBook(@Valid Book book);
    Book updateBook(Long id, @Valid Book book);
    void deleteBook(Long id);
    Set<BorrowingRecord> getBorrowingRecordsForBook(Long bookId);
}

