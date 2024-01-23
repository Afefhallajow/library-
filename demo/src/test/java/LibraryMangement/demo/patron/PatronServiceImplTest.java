package LibraryMangement.demo.patron;
import LibraryMangement.demo.Entity.Patron;
import LibraryMangement.demo.Repo.PatronRepository;
import LibraryMangement.demo.Service.PatronServiceImpl;
import LibraryMangement.demo.ServiceInterface.PatronService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PatronServiceImplTest {

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private PatronService patronService = new PatronServiceImpl();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPatrons_ReturnsListOfPatrons() {
        // Arrange
        List<Patron> expectedPatrons = Arrays.asList(
                new Patron(1L, "John Doe", "john@example.com"),
                new Patron(2L, "Jane Doe", "jane@example.com")
        );

        when(patronRepository.findAll()).thenReturn(expectedPatrons);

        // Act
        List<Patron> actualPatrons = patronService.getAllPatrons();

        // Assert
        assertEquals(expectedPatrons, actualPatrons);
    }

    @Test
    void getPatronById_ExistingPatronId_ReturnsPatron() {
        // Arrange
        Long patronId = 1L;
        Patron expectedPatron = new Patron(patronId, "John Doe", "john@example.com");

        when(patronRepository.findById(patronId)).thenReturn(Optional.of(expectedPatron));

        // Act
        Patron actualPatron = patronService.getPatronById(patronId);

        // Assert
        assertEquals(expectedPatron, actualPatron);
    }

    @Test
    void getPatronById_NonExistingPatronId_ThrowsEntityNotFoundException() {
        // Arrange
        Long nonExistingPatronId = 99L;

        when(patronRepository.findById(nonExistingPatronId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> patronService.getPatronById(nonExistingPatronId));
    }

    @Test
    void addPatron_ValidPatron_ReturnsAddedPatron() {
        // Arrange
        Patron patronToAdd = new Patron(null, "New Patron", "new@example.com");
        Patron expectedAddedPatron = new Patron(1L, "New Patron", "new@example.com");

        when(patronRepository.save(any(Patron.class))).thenReturn(expectedAddedPatron);

        // Act
        Patron actualAddedPatron = patronService.addPatron(patronToAdd);

        // Assert
        assertEquals(expectedAddedPatron, actualAddedPatron);
    }

    @Test
    void updatePatron_ExistingPatronIdAndValidPatron_ReturnsUpdatedPatron() {
        // Arrange
        Long existingPatronId = 1L;
        Patron patronToUpdate = new Patron(null, "Updated Patron", "updated@example.com");
        Patron expectedUpdatedPatron = new Patron(existingPatronId, "Updated Patron", "updated@example.com");

        when(patronRepository.existsById(existingPatronId)).thenReturn(true);
        when(patronRepository.save(any(Patron.class))).thenReturn(expectedUpdatedPatron);

        // Act
        Patron actualUpdatedPatron = patronService.updatePatron(existingPatronId, patronToUpdate);

        // Assert
        assertEquals(expectedUpdatedPatron, actualUpdatedPatron);
    }

    @Test
    void updatePatron_NonExistingPatronId_ThrowsEntityNotFoundException() {
        // Arrange
        Long nonExistingPatronId = 99L;
        Patron patronToUpdate = new Patron(null, "Updated Patron", "updated@example.com");

        when(patronRepository.existsById(nonExistingPatronId)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> patronService.updatePatron(nonExistingPatronId, patronToUpdate));
    }

    @Test
    void deletePatron_ExistingPatronId_DeletesPatron() {
        // Arrange
        Long existingPatronId = 1L;

        when(patronRepository.existsById(existingPatronId)).thenReturn(true);

        // Act
        patronService.deletePatron(existingPatronId);

        // Assert
        verify(patronRepository, times(1)).deleteById(existingPatronId);
    }

    @Test
    void deletePatron_NonExistingPatronId_ThrowsEntityNotFoundException() {
        // Arrange
        Long nonExistingPatronId = 99L;

        when(patronRepository.existsById(nonExistingPatronId)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> patronService.deletePatron(nonExistingPatronId));
    }
}
