package LibraryMangement.demo.Service;

import LibraryMangement.demo.Entity.Book;
import LibraryMangement.demo.Entity.BorrowingRecord;
import LibraryMangement.demo.Repo.BookRepository;
import LibraryMangement.demo.Repo.BorrowingRecordRepository;
import LibraryMangement.demo.ServiceInterface.BookService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;
    @Cacheable("books")
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    @Cacheable("books")
    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + id));
    }
    @CacheEvict(cacheNames = "books", allEntries = true)
    @Override
    public Book addBook(@Valid Book book) {
        return bookRepository.save(book);
    }
    @CacheEvict(cacheNames = "books", allEntries = true)
    @Override
    public Book updateBook(Long id, @Valid Book book) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found with ID: " + id);
        }

        book.setId(id);
        return bookRepository.save(book);
    }
    @CacheEvict(cacheNames = "books", allEntries = true)
    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found with ID: " + id);
        }

        bookRepository.deleteById(id);
    }
    @Transactional
    @Override
    public Set<BorrowingRecord> getBorrowingRecordsForBook(Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            return  book.getBorrowingRecords();
        } else {
            throw new EntityNotFoundException("Book not found with ID: " + bookId);
        }
    }
}
