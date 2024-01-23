package LibraryMangement.demo.Controller;

import LibraryMangement.demo.ServiceInterface.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrowings")
public class BorrowingRecordController {

    @Autowired
    private BorrowingRecordService borrowingRecordService;

    @PostMapping("/{bookId}/patron/{patronId}")
    public ResponseEntity<Void> borrowBook(
            @PathVariable Long bookId,
            @PathVariable Long patronId) {
        borrowingRecordService.borrowBook(bookId, patronId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<Void> returnBook(
            @PathVariable Long bookId,
            @PathVariable Long patronId) {
        borrowingRecordService.returnBook(bookId, patronId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}