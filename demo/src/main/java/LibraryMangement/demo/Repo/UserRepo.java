package LibraryMangement.demo.Repo;

import LibraryMangement.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

public interface UserRepo  extends  JpaRepository<User,Integer> {
Optional<User> findByUsername(String username);
}
