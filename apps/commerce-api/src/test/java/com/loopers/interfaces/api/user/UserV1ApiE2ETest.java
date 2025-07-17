package com.loopers.interfaces.api.user;

import com.loopers.interfaces.api.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserV1ApiE2ETest {

    /*
     * 회원가입 E2E 테스트
     * - [o]  회원 가입이 성공할 경우, 생성된 유저 정보를 응답으로 반환한다.
     * - [o]  회원 가입 시에 성별이 없을 경우, `400 Bad Request` 응답을 반환한다.
     */

    @Autowired
    private TestRestTemplate testRestTemplate;

    @DisplayName("POST /api/v1/users")
    @Nested
    class Signup {

        private static final String ENDPOINT = "/api/v1/users";

        @DisplayName("회원 가입이 성공할 경우, 생성된 유저 정보를 응답으로 반환한다.")
        @Test
        void returnsUserInfo_whenSignupIsSuccessful() {
            // arrange
            String signupRequest = """
                    {
                        "name": "정찬호",
                        "email": "test@gmail.com",
                        "gender": "M",
                        "birth": "2000-01-01"
                    }
                    """;

            // act
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            ParameterizedTypeReference<ApiResponse<UserV1Dto.UserResponse>> responseType = new ParameterizedTypeReference<>() {
            };
            ResponseEntity<ApiResponse<UserV1Dto.UserResponse>> response =
                    testRestTemplate.exchange(ENDPOINT, HttpMethod.POST, new HttpEntity<>(signupRequest, httpHeaders), responseType);

            // assert
            assertAll(
                    () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(response.getBody()).isNotNull(),
                    () -> assertThat(response.getBody().data().name()).isEqualTo("정찬호"),
                    () -> assertThat(response.getBody().data().gender()).isEqualTo(UserV1Dto.Gender.M)
            );
        }

        @DisplayName("회원 가입 시에 성별이 없을 경우, `400 Bad Request` 응답을 반환한다.")
        @Test
        void returnsBadRequest_whenGenderIsMissing() {
            // arrange
            String signupRequest = """
                    {
                        "name": "정찬호",
                        "email": "test@gmail.com",
                        "gender": null,
                        "birth": "2000-01-01"
                    }
                    """;

            // act
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            ParameterizedTypeReference<ApiResponse<UserV1Dto.UserResponse>> responseType = new ParameterizedTypeReference<>() {
            };
            ResponseEntity<ApiResponse<UserV1Dto.UserResponse>> response =
                    testRestTemplate.exchange(ENDPOINT, HttpMethod.POST, new HttpEntity<>(signupRequest, httpHeaders), responseType);

            // assert
            assertAll(
                    () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST),
                    () -> assertThat(response.getBody().meta().result()).isEqualTo(ApiResponse.Metadata.Result.FAIL),
                    () -> assertThat(response.getBody().data()).isNull()
            );
        }


    }

}
