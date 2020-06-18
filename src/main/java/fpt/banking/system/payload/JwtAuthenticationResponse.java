package fpt.banking.system.payload;

public class JwtAuthenticationResponse {
	private String accessToken;
    private String tokenType = "Bearer";
    private String roles;

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
