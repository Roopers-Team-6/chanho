package com.loopers.domain.point;

import com.loopers.application.user.UserFacade;
import com.loopers.domain.user.UserGender;
import com.loopers.interfaces.api.user.UserV1Dto;
import com.loopers.utils.DatabaseCleanUp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PointServiceIntegrationTest {

    /*
     * - [o]  해당 ID 의 회원이 존재할 경우, 보유 포인트가 반환된다.
     * - [o]  해당 ID 의 회원이 존재하지 않을 경우, null 이 반환된다.
     */

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private PointService pointService;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @AfterEach
    void tearDown() {
        databaseCleanUp.truncateAllTables();
    }

    @DisplayName("포인트 조회")
    @Nested
    class GetPoints {
        @DisplayName("해당 ID 의 회원이 존재할 경우, 보유 포인트가 반환된다.")
        @Test
        void returnsPoints_whenUserExists() {
            // arrange
            UserV1Dto.UserResponse saved = userFacade.signUp(new UserV1Dto.SignupRequest(
                    "mwma91",
                    "test@gmail.com",
                    UserGender.M,
                    LocalDate.of(2000, 1, 1)
            ));

            // act
            PointEntity point = pointService.findByUserId(saved.id());

            // assert
            assertThat(point).isNotNull();
            assertThat(point.getAmount()).isEqualTo(0);
        }

        @DisplayName("해당 ID 의 회원이 존재하지 않을 경우, null 이 반환된다.")
        @Test
        void returnsNull_whenUserDoesNotExist() {
            // arrange
            Long nonExistentUserId = 999L;

            // act
            PointEntity point = pointService.findByUserId(nonExistentUserId);

            // assert
            assertThat(point).isNull();
        }
    }
}
