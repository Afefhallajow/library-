package LibraryMangement.demo.security;

import LibraryMangement.demo.Entity.User;
import LibraryMangement.demo.Repo.UserRepo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
    @RequestMapping(value = "/")
    public class Authcontroller {
        public String username;

        TokenUtil tokenUtil =new TokenUtil();
        @Autowired
        UserService userService;
        @Autowired
        UserRepo repo;

        @Autowired
        private AuthenticationManager authenticationManager;
        @Autowired
        private PasswordEncoder passwordEncoder;

        public JwtResponse response1;



        @PostMapping(value = "login")
        public ResponseEntity<JwtResponse> SigneIn(@RequestBody @Valid SigneRequest signeRequest)
        {

            UserDetails userDetails = userService.loadUserByUsername(signeRequest.getUsername());

            // Check if the provided password matches the stored (encoded) password
            System.out.println(userDetails.getUsername());

            System.out.println(userDetails.getPassword());
            if (passwordEncoder.matches(signeRequest.getPassword(), userDetails.getPassword())) {
                // Passwords match, proceed with authentication and token generation
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(signeRequest.getUsername(), signeRequest.getPassword())
                );

                String token = this.tokenUtil.generateToken(userDetails.getUsername());
                JwtResponse jwtResponse = new JwtResponse(token);

                ResponseEntity responseEntity = new ResponseEntity<>(jwtResponse, HttpStatus.CREATED);
                return responseEntity;
            } else {
                // Passwords do not match
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        @PostMapping(value = "register")
        public ResponseEntity<String> Register(@RequestBody @Valid SigneRequest signeRequest,HttpServletResponse response2) {
            User user = new User();
            user.setUsername(signeRequest.getUsername());
            user.setPassword(  passwordEncoder.encode(signeRequest.getPassword()));
            repo.save(user);
            return new ResponseEntity<>("created secss",HttpStatus.CREATED);
        }
    }


