package LibraryMangement.demo.security;

import jakarta.validation.constraints.NotBlank;

public class SigneRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    String Email;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    SigneRequest()
    {

    }
    SigneRequest(String username,String password)
    {
        this.username=username;
        this.password=password;

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}