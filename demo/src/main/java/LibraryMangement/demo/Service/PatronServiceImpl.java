package LibraryMangement.demo.Service;

import LibraryMangement.demo.Entity.Patron;
import LibraryMangement.demo.Repo.PatronRepository;
import LibraryMangement.demo.ServiceInterface.PatronService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatronServiceImpl implements PatronService {

    @Autowired
    private PatronRepository patronRepository;
    @Cacheable("patrons")
    @Override
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }
    @Cacheable("patrons")
    @Override
    public Patron getPatronById(Long id) {
        return patronRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patron not found with ID: " + id));
    }
    @CacheEvict(cacheNames = "patrons", allEntries = true)
    @Override
    public Patron addPatron(@Valid Patron patron) {

        return patronRepository.save(patron);
    }
    @CacheEvict(cacheNames = "patrons", allEntries = true)
    @Override
    public Patron updatePatron(Long id, @Valid Patron patron) {
        if (!patronRepository.existsById(id)) {
            throw new EntityNotFoundException("Patron not found with ID: " + id);
        }

        patron.setId(id);
        return patronRepository.save(patron);
    }
    @CacheEvict(cacheNames = "patrons", allEntries = true)
    @Override
    public void deletePatron(Long id) {
        if (!patronRepository.existsById(id)) {
            throw new EntityNotFoundException("Patron not found with ID: " + id);
        }

        patronRepository.deleteById(id);
    }
}