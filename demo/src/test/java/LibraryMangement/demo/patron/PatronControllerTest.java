package LibraryMangement.demo.patron;

import LibraryMangement.demo.Controller.BookController;
import LibraryMangement.demo.Controller.PatronController;
import LibraryMangement.demo.Entity.Book;
import LibraryMangement.demo.Entity.Patron;
import LibraryMangement.demo.ServiceInterface.BookService;
import LibraryMangement.demo.ServiceInterface.PatronService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class PatronControllerTest {
    @Mock
    private PatronService patronService;
    @InjectMocks
    private PatronController patronController;

    public PatronControllerTest() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllPatrons_ReturnsListOfPatrons() {
        List<Patron> expectedPatron = Arrays.asList( new Patron(),new Patron());
        when(patronService.getAllPatrons()).thenReturn(expectedPatron);
        // Act
        ResponseEntity<List<Patron>> responseEntity = patronController.getAllPatrons();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedPatron, responseEntity.getBody());

    }
    @Test
    void getPatronById_ExistingId_ReturnsPatron() {
        // Arrange
        Long existingId = 1L;
        Patron expectedPatron = new Patron();
        when(patronService.getPatronById(existingId)).thenReturn(expectedPatron);

        // Act
        ResponseEntity<Patron> responseEntity = patronController.getPatronById(existingId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedPatron, responseEntity.getBody());
    }



    @Test
    void updatePatron_ExistingIdAndValidPatron_ReturnsOk() {
        // Arrange
        Long existingId = 1L;
        Patron patronToUpdate = new Patron();
        when(patronService.updatePatron(existingId, patronToUpdate)).thenReturn(patronToUpdate);

        // Act
        ResponseEntity<Patron> responseEntity = patronController.updatePatron(existingId, patronToUpdate);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(patronToUpdate, responseEntity.getBody());
    }


    @Test
    void deletePatron_ExistingId_ReturnsNoContent() {
        // Arrange
        Long existingId = 1L;

        // Act
        ResponseEntity<Void> responseEntity = patronController.deletePatron(existingId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }


}
