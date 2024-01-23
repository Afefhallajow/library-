package LibraryMangement.demo.book;

import LibraryMangement.demo.Controller.BookController;
import LibraryMangement.demo.Entity.Book;
import LibraryMangement.demo.Entity.BorrowingRecord;
import LibraryMangement.demo.ServiceInterface.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class BookControllerTest {
@Mock
private BookService bookService;
@InjectMocks
private BookController bookController;

    public BookControllerTest() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllBooks_ReturnsListOfBooks() {
List<Book> expectedBooks = Arrays.asList( new Book(),new Book());
        when(bookService.getAllBooks()).thenReturn(expectedBooks);
        // Act
        ResponseEntity<List<Book>> responseEntity = bookController.getAllBooks();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedBooks, responseEntity.getBody());

    }
    @Test
    void getBookById_ReturnsBook() {
        // Arrange
        Long bookId = 1L;
        Book expectedBook = new Book();
        when(bookService.getBookById(bookId)).thenReturn(expectedBook);
        // Act
        ResponseEntity<Book> responseEntity = bookController.getBookById(bookId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedBook, responseEntity.getBody());


    }
    @Test
    void getBorrowListforBookById_ReturnsBorrowingRecords() {
        // Arrange
        Long bookId = 1L;
        Set<BorrowingRecord> expectedRecords = new HashSet<>(Arrays.asList(new BorrowingRecord(), new BorrowingRecord()));
        when(bookService.getBorrowingRecordsForBook(bookId)).thenReturn(expectedRecords);

        // Act
        ResponseEntity<Set<BorrowingRecord>> responseEntity = bookController.getBorrowListforBookById(bookId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedRecords, responseEntity.getBody());
    }

    @Test
    void addBook_ReturnsCreatedStatus() {
        // Arrange
        Book bookToAdd = new Book();
        when(bookService.addBook(bookToAdd)).thenReturn(bookToAdd);

        // Act
        ResponseEntity<Book> responseEntity = bookController.addBook(bookToAdd);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(bookToAdd, responseEntity.getBody());
    }

    @Test
    void updateBook_ReturnsOkStatus() {
        // Arrange
        Long bookId = 1L;
        Book updatedBook = new Book();
        when(bookService.updateBook(eq(bookId), any())).thenReturn(updatedBook);

        // Act
        ResponseEntity<Book> responseEntity = bookController.updateBook(bookId, updatedBook);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedBook, responseEntity.getBody());
    }

    @Test
    void deleteBook_ReturnsNoContentStatus() {
        // Arrange
        Long bookId = 1L;

        // Act
        ResponseEntity<Void> responseEntity = bookController.deleteBook(bookId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(bookService, times(1)).deleteBook(bookId);
    }

    }
