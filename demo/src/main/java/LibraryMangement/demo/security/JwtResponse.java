package LibraryMangement.demo.security;
public class JwtResponse {
    private  String token;
    JwtResponse()
    {}
    JwtResponse(String token)
    {this.token=token;}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
