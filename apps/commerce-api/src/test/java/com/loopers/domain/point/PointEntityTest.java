package com.loopers.domain.point;

import com.loopers.domain.user.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;

public class PointEntityTest {

    /*
     * 포인트 엔티티 테스트
     * - [o] 0 이하의 정수로 포인트를 충전 시 실패한다.
     */

    @DisplayName("포인트를 충전할 때, ")
    @Nested
    class Charge {
        @DisplayName("0 이하의 정수로 포인트를 충전 시 실패한다.")
        @Test
        void failedToChargePoint_whenAmountIsZeroOrNegative() {
            PointEntity point = new PointEntity(new UserEntity());
            assertThrows(IllegalArgumentException.class, () -> point.charge(0L));
            assertThrows(IllegalArgumentException.class, () -> point.charge(-100L));
        }
    }
}
