package LibraryMangement.demo.Repo;

import LibraryMangement.demo.Entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron, Long> {
}