package com.teamchallenge.markethub;

import com.teamchallenge.markethub.dto.authorization.AuthorizationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class MarketHubJsonTest {

    @Autowired
    private JacksonTester<AuthorizationRequest> jsonAuth;

    private AuthorizationRequest authorizationRequest;
    @BeforeEach
    public void setup() {
        authorizationRequest = new AuthorizationRequest("Bilbo",
                "Baggins","bilbo@gmail.com","380991419249","pass123");
    }

    @Test
    public void authRequestSerializationTest() throws IOException {
        assertThat(jsonAuth.write(authorizationRequest)).isStrictlyEqualToJson("authorizationRequest.json");
        assertThat(jsonAuth.write(authorizationRequest)).extractingJsonPathStringValue("@.firstname")
                .isEqualTo("Bilbo");
        assertThat(jsonAuth.write(authorizationRequest)).extractingJsonPathStringValue("@.email")
                .isEqualTo("bilbo@gmail.com");
        assertThat(jsonAuth.write(authorizationRequest)).extractingJsonPathStringValue("@.password")
                .isEqualTo("pass123");
    }

    @Test
    public void authRequestDeserializationTest() throws IOException {
        String expected = """
                {
                   "firstname": "Bilbo",
                   "lastname": "Baggins",
                   "email": "bilbo@gmail.com",
                   "phone": "380991419249",
                   "password": "pass123"
                }
                """;
        assertThat(jsonAuth.parse(expected))
                .isEqualTo(authorizationRequest);
        assertThat(jsonAuth.parseObject(expected).email()).isEqualTo("bilbo@gmail.com");
        assertThat(jsonAuth.parseObject(expected).password()).isEqualTo("pass123");
    }
}
