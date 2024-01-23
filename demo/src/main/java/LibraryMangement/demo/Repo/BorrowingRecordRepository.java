package LibraryMangement.demo.Repo;

import LibraryMangement.demo.Entity.Book;
import LibraryMangement.demo.Entity.BorrowingRecord;
import LibraryMangement.demo.Entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    BorrowingRecord findByBookAndPatronAndReturnDateIsNull(Book book, Patron patron);

    List<BorrowingRecord> findByBook(Book book);
    List<BorrowingRecord> findByBookAndReturnDateNull(Book book);

}