package tech.jwt.payload.Request;

import jakarta.validation.constraints.*;

import java.util.Set;

public class SignUpRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

	private Set<String> role;
//    public SignUpRequest() {
//
//    }

    public SignUpRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

	public String getUsername() {
	    return username;
	}

	public void setUsername(String username) {
	    this.username = username;
	}

	public String getPassword() {
	    return password;
	}

	public void setPassword(String password) {
	    this.password = password;
	}

	public String getEmail() {
	    return email;
	}

	public void setEmail(String email) {
	    this.email = email;
	}

	public Set<String> getRoles() {
		return this.role;
	}

	public void setRoles(Set<String> role) {
		this.role = role;
	}
}
