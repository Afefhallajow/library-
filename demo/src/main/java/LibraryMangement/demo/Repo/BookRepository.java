package LibraryMangement.demo.Repo;

import LibraryMangement.demo.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}