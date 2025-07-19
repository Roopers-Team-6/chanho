package com.loopers.interfaces.api.user;

import com.loopers.domain.user.UserEntity;
import com.loopers.domain.user.UserGender;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;

import java.time.LocalDate;

public class UserV1Dto {
    public record SignupRequest(
            String username,
            String email,
            UserGender gender,
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
            String username,
            String email,
            UserGender gender,
            LocalDate birth
    ) {
        public static UserResponse from(UserEntity user) {
            return new UserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getGender(),
                    user.getBirth()
            );
        }
    }

}
