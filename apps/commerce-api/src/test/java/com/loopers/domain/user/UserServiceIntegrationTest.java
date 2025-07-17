package com.loopers.domain.user;

import com.loopers.application.user.UserCommand;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import com.loopers.utils.DatabaseCleanUp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceIntegrationTest {
    /*
     * - [o]  회원 가입시 User 저장이 수행된다. ( spy 검증 )
     * - [o]  이미 가입된 ID 로 회원가입 시도 시, 실패한다.
     */

    @Autowired
    private UserService userService;

    @MockitoSpyBean
    private UserRepository userRepository;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @AfterEach
    void tearDown() {
        databaseCleanUp.truncateAllTables();
    }

    @DisplayName("회원가입")
    @Nested
    class Signup {
        @DisplayName("회원 가입시 User 저장이 수행된다. ( spy 검증 )")
        @Test
        void savesUser_whenSignupIsSuccessful() {
            // arrange
            UserCommand.Create newUser = new UserCommand.Create(
                    "test",
                    "test@gmail.com",
                    UserGender.M,
                    LocalDate.of(2000, 1, 1));

            // act
            UserEntity savedUser = userService.save(newUser);

            verify(userRepository).save(argThat(user ->
                    user.getId() != null
                            && user.getUsername().equals(newUser.username())
                            && user.getEmail().equals(newUser.email()))
            );

            // assert
            assertThat(savedUser.getId()).isNotNull();
        }

        @DisplayName("이미 가입된 ID 로 회원가입 시도 시, 실패한다.")
        @Test
        void throwsException_whenUserAlreadyExists() {
            // arrange
            UserCommand.Create newUser = new UserCommand.Create(
                    "test",
                    "test@gmail.com",
                    UserGender.M,
                    LocalDate.of(2000, 1, 1));

            // act
            UserEntity saved = userService.save(newUser);

            // assert
            CoreException exception = assertThrows(CoreException.class, () -> userService.save(newUser));
            assertThat(exception.getErrorType()).isEqualTo(ErrorType.CONFLICT);
        }
    }
}
