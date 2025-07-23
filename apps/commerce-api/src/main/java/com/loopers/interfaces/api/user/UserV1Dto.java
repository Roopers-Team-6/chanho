package com.loopers.interfaces.api.user;

import com.loopers.domain.user.UserEntity;
import com.loopers.domain.user.UserGender;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public class UserV1Dto {
    public record SignupRequest(
            @NotNull(message = "이름은 필수 입력입니다.")
            @Pattern(regexp = "^[A-Za-z0-9]{1,10}$",
                    message = "이름은 1~10자의 영문 대소문자 또는 숫자만 포함할 수 있습니다.")
            String username,
            @NotNull(message = "이메일은 필수 입력입니다.")
            @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
                    message = "이메일 형식이 올바르지 않습니다.")
            String email,
            @NotNull(message = "성별은 필수 입력입니다.")
            UserGender gender,
            @NotNull(message = "생일은 필수 입력입니다.")
            @Past(message = "생일은 오늘 이전 날짜여야 합니다.")
            LocalDate birth
    ) {
        public SignupRequest {
            if (birth.isBefore(LocalDate.of(1900, 1, 1))) {
                throw new CoreException(ErrorType.BAD_REQUEST, "생일은 1900년 이후 날짜여야 합니다.");
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
