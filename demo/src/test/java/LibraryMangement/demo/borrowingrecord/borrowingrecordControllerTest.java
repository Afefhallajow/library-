package LibraryMangement.demo.borrowingrecord;

import LibraryMangement.demo.Controller.BorrowingRecordController;
import LibraryMangement.demo.ServiceInterface.BorrowingRecordService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class borrowingrecordControllerTest {
@Mock
    private BorrowingRecordService borrowingRecordService;
@InjectMocks
    private BorrowingRecordController borrowingRecordController;

    public borrowingrecordControllerTest() {
        MockitoAnnotations.openMocks(this);
    }
@Test
public  void borrowboook_ReturnOk()
{
    long bookid=1L;
    long patronId=1L;
doNothing().when(borrowingRecordService).borrowBook(bookid,patronId);
// Act
    ResponseEntity<Void> responseEntity = borrowingRecordController.borrowBook(bookid, patronId);

    assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
}
    @Test
    void returnBook_ValidIds_ReturnsOk() {
        // Arrange
        Long bookId = 1L;
        Long patronId = 2L;
        doNothing().when(borrowingRecordService).returnBook(bookId, patronId);

        // Act
        ResponseEntity<Void> responseEntity = borrowingRecordController.returnBook(bookId, patronId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}


