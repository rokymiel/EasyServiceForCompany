package eservice.business.core;

import com.google.cloud.firestore.annotation.PropertyName;

import java.util.Objects;

public class Token {
    @PropertyName("token")
    private String token;

    @PropertyName("token")
    public String getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token1 = (Token) o;
        return Objects.equals(getToken(), token1.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getToken());
    }
}
