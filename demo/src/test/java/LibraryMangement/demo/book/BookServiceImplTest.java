package LibraryMangement.demo.book;

import LibraryMangement.demo.Entity.Book;
import LibraryMangement.demo.Entity.BorrowingRecord;
import LibraryMangement.demo.Repo.BookRepository;
import LibraryMangement.demo.Repo.BorrowingRecordRepository;
import LibraryMangement.demo.Service.BookServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBooks_ReturnsListOfBooks() {
        // Arrange
        List<Book> expectedBooks = Arrays.asList(
                new Book(1L, "Book 1", "Author 1", 2020, "ISBN-1"),
                new Book(2L, "Book 2", "Author 2", 2021, "ISBN-2")
        );
        when(bookRepository.findAll()).thenReturn(expectedBooks);

        // Act
        List<Book> actualBooks = bookService.getAllBooks();

        // Assert
        assertEquals(expectedBooks, actualBooks);
    }

    @Test
    void getBookById_ExistingId_ReturnsBook() {
        // Arrange
        Long existingId = 1L;
        Book expectedBook = new Book(existingId, "Book 1", "Author 1", 2020, "ISBN-1");
        when(bookRepository.findById(existingId)).thenReturn(Optional.of(expectedBook));

        // Act
        Book actualBook = bookService.getBookById(existingId);

        // Assert
        assertEquals(expectedBook, actualBook);
    }

    @Test
    void getBookById_NonExistingId_ThrowsEntityNotFoundException() {
        // Arrange
        Long nonExistingId = 2L;
        when(bookRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> bookService.getBookById(nonExistingId));
    }

    @Test
    void addBook_ValidBook_ReturnsAddedBook() {
        // Arrange
        Book newBook = new Book(null, "New Book", "New Author", 2022, "ISBN-NEW");
        Book expectedAddedBook = new Book(1L, "New Book", "New Author", 2022, "ISBN-NEW");
        when(bookRepository.save(newBook)).thenReturn(expectedAddedBook);

        // Act
        Book actualAddedBook = bookService.addBook(newBook);

        // Assert
        assertEquals(expectedAddedBook, actualAddedBook);
    }

    @Test
    void updateBook_ExistingId_ReturnsUpdatedBook() {
        // Arrange
        Long existingId = 1L;
        Book existingBook = new Book(existingId, "Book 1", "Author 1", 2020, "ISBN-1");
        Book updatedBook = new Book(existingId, "Updated Book", "Updated Author", 2023, "ISBN-UPDATED");

        when(bookRepository.existsById(existingId)).thenReturn(true);
        when(bookRepository.save(updatedBook)).thenReturn(updatedBook);

        // Act
        Book actualUpdatedBook = bookService.updateBook(existingId, updatedBook);

        // Assert
        assertEquals(updatedBook, actualUpdatedBook);
    }

    @Test
    void updateBook_NonExistingId_ThrowsEntityNotFoundException() {
        // Arrange
        Long nonExistingId = 2L;
        Book updatedBook = new Book(nonExistingId, "Updated Book", "Updated Author", 2023, "ISBN-UPDATED");

        when(bookRepository.existsById(nonExistingId)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> bookService.updateBook(nonExistingId, updatedBook));
    }

    @Test
    void deleteBook_ExistingId_DeletesBook() {
        // Arrange
        Long existingId = 1L;
        when(bookRepository.existsById(existingId)).thenReturn(true);

        // Act
        bookService.deleteBook(existingId);

        // Assert
        verify(bookRepository, times(1)).deleteById(existingId);
    }

    @Test
    void deleteBook_NonExistingId_ThrowsEntityNotFoundException() {
        // Arrange
        Long nonExistingId = 2L;
        when(bookRepository.existsById(nonExistingId)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> bookService.deleteBook(nonExistingId));
    }

    @Test
    void getBorrowingRecordsForBook_NonExistingBookId_ThrowsEntityNotFoundException() {
        // Arrange
        Long nonExistingBookId = 2L;
        when(bookRepository.findById(nonExistingBookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> bookService.getBorrowingRecordsForBook(nonExistingBookId));
    }
}
