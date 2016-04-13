package basc.controller;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthenticationToken {

    final String token;
    final LocalDateTime expires;

    public AuthenticationToken(String token, LocalDateTime expires) {
        if(!isValid(token)) {
            throw new IllegalArgumentException("Invalid token: " + token);
        }

        this.token = token;
        this.expires = expires;
    }

    @Override
    public String toString() {
        return token;
    }

    private boolean isValid(String token) {
        return null != token && !token.isEmpty() && token.startsWith("A") && token.length() == 108;
    }

    public boolean isValidAt(LocalDateTime check) {
        return check.isBefore(expires) || check.equals(expires);
    }
}
