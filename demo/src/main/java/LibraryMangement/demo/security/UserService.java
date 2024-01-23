package LibraryMangement.demo.security;

import LibraryMangement.demo.Entity.User;
import LibraryMangement.demo.Repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     User userInfo = repository.findByUsername(username)
        .orElseThrow(() -> new EntityNotFoundException("user not found with username: " + username));
    return userInfo;
    }}
