package com.loopers.interfaces.api.user;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;

import java.time.LocalDate;

public class UserV1Dto {
    public record SignupRequest(
            String name,
            String email,
            Gender gender,
            LocalDate birth
    ) {
        public SignupRequest {
            if (gender == null) {
                throw new CoreException(ErrorType.BAD_REQUEST, "성별은 필수 입력입니다.");
            }
        }
    }

    public record UserResponse(
            Long id,
            String name,
            String email,
            Gender gender,
            LocalDate birth
    ) {
    }

    enum Gender {
        M,
        F
    }
}
