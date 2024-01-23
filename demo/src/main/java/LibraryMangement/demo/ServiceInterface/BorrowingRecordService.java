package LibraryMangement.demo.ServiceInterface;

public interface BorrowingRecordService {
    void borrowBook(Long bookId, Long patronId);
    void returnBook(Long bookId, Long patronId);
}