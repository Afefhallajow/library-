package LibraryMangement.demo.ServiceInterface;

import LibraryMangement.demo.Entity.Patron;
import jakarta.validation.Valid;

import java.util.List;

public interface PatronService {
    List<Patron> getAllPatrons();
    Patron getPatronById(Long id);
    Patron addPatron(@Valid Patron patron);
    Patron updatePatron(Long id, @Valid Patron patron);
    void deletePatron(Long id);
}