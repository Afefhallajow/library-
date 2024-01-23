package LibraryMangement.demo.Service;

import LibraryMangement.demo.Entity.Book;
import LibraryMangement.demo.Entity.BorrowingRecord;
import LibraryMangement.demo.Entity.Patron;
import LibraryMangement.demo.Repo.BookRepository;
import LibraryMangement.demo.Repo.BorrowingRecordRepository;
import LibraryMangement.demo.Repo.PatronRepository;
import LibraryMangement.demo.ServiceInterface.BorrowingRecordService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Max;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowingRecordServiceImpl implements BorrowingRecordService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Transactional
    @Override
    public void borrowBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + bookId));

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new EntityNotFoundException("Patron not found with ID: " + patronId));

        List<BorrowingRecord> checkborrowingRecord = borrowingRecordRepository.findByBookAndReturnDateNull(book);
    //check if the book already in borrow
        if (checkborrowingRecord.size()>0 )
    {
    throw new EntityNotFoundException("book already in borrow with ID : " + bookId);
    }
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());

        borrowingRecordRepository.save(borrowingRecord);
    }
    @Transactional
    @Override
    public void returnBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + bookId));

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new EntityNotFoundException("Patron not found with ID: " + patronId));

        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookAndPatronAndReturnDateIsNull(book, patron);

        if (borrowingRecord == null) {
            throw new EntityNotFoundException("No active borrowing record found for Book ID: " + bookId + " and Patron ID: " + patronId);
        }

        borrowingRecord.setReturnDate(LocalDate.now());
        borrowingRecordRepository.save(borrowingRecord);
    }
}