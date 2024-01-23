package LibraryMangement.demo.borrowingrecord;

import LibraryMangement.demo.Entity.Book;
import LibraryMangement.demo.Entity.BorrowingRecord;
import LibraryMangement.demo.Entity.Patron;
import LibraryMangement.demo.Repo.BookRepository;
import LibraryMangement.demo.Repo.BorrowingRecordRepository;
import LibraryMangement.demo.Repo.PatronRepository;
import LibraryMangement.demo.Service.BorrowingRecordServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BorrowingRecordServiceImplTest {
    @Mock
    private PatronRepository patronRepository;
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @InjectMocks
    private BorrowingRecordServiceImpl borrowingRecordService;

    public BorrowingRecordServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void borrowBook_ValidBookAndPatron_ReturnsNoException() {
        // Arrange
        Long bookId = 1L;
        Long patronId = 1L;

        Book book = new Book();
        book.setId(bookId);

        Patron patron = new Patron();
        patron.setId(patronId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
        when(borrowingRecordRepository.findByBookAndReturnDateNull(any())).thenReturn(new ArrayList<>());

        // Act
        borrowingRecordService.borrowBook(bookId, patronId);

        // No exception is expected

        // Assert
        Mockito.verify(borrowingRecordRepository).save(any());
    }



    @Test
    void returnBook_ValidBookAndPatron_ReturnsNoException() {
        // Arrange
        Long bookId = 1L;
        Long patronId = 1L;

        Book book = new Book();
        book.setId(bookId);

        Patron patron = new Patron();
        patron.setId(patronId);

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
        when(borrowingRecordRepository.findByBookAndPatronAndReturnDateIsNull(book, patron)).thenReturn(borrowingRecord);

        // Act
        borrowingRecordService.returnBook(bookId, patronId);

        // No exception is expected

        // Assert
        Mockito.verify(borrowingRecordRepository).save(any());
    }





}
