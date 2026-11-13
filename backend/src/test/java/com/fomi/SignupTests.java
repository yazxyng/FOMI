package com.fomi;
import com.fomi.domain.profile.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.net.URI;
import java.net.http.*;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SignupTests {
    @LocalServerPort int port;
    @Autowired UserRepository users;
    @Autowired PasswordEncoder encoder;
    HttpResponse<String> signup(String body) throws Exception {
        return HttpClient.newHttpClient().send(HttpRequest.newBuilder(URI.create("http://localhost:"+port+"/api/auth/signup"))
            .header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(body)).build(), HttpResponse.BodyHandlers.ofString());
    }
    @Test void signupStoresHashAndRejectsDuplicate() throws Exception {
        String body = "{\"username\":\"test_user\",\"nickname\":\"  예령  \",\"password\":\"password1234\",\"passwordConfirm\":\"password1234\"}";
        var response = signup(body);
        assertThat(response.statusCode()).isEqualTo(201);
        assertThat(response.body()).contains("예령").doesNotContain("password", "token");
        var user = users.findByUsername("test_user").orElseThrow();
        assertThat(user.getPasswordHash()).isNotEqualTo("password1234");
        assertThat(encoder.matches("password1234", user.getPasswordHash())).isTrue();
        assertThat(signup(body).statusCode()).isEqualTo(409);
    }
    @Test void missingNicknameGetsDefault() throws Exception {
        var response = signup("{\"username\":\"default_user\",\"password\":\"password1234\",\"passwordConfirm\":\"password1234\"}");
        assertThat(response.statusCode()).isEqualTo(201);
        assertThat(response.body()).contains("포미친구");
    }
    @Test void invalidInputsNeverCreateUser() throws Exception {
        for (String body : new String[] {
            "{}",
            "{\"username\":\"invalid\",\"password\":\"password1234\",\"passwordConfirm\":\"different1234\"}",
            "{\"username\":\"UPPER\",\"password\":\"password1234\",\"passwordConfirm\":\"password1234\"}",
            "{\"username\":\"invalid\",\"nickname\":\"1234567890123\",\"password\":\"password1234\",\"passwordConfirm\":\"password1234\"}"
        }) assertThat(signup(body).statusCode()).isEqualTo(400);
        assertThat(users.existsByUsername("invalid")).isFalse();
    }
}
