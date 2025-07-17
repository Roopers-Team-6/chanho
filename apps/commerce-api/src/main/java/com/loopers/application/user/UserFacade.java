package com.loopers.application.user;

import com.loopers.domain.user.UserEntity;
import com.loopers.domain.user.UserService;
import com.loopers.interfaces.api.user.UserV1Dto;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;

    public UserV1Dto.UserResponse find(Long id) {
        UserEntity user = userService.findById(id)
                .orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND, "User not found with ID: [%s]".formatted(id)));

        return UserV1Dto.UserResponse.from(user);
    }

    public UserV1Dto.UserResponse signUp(UserV1Dto.SignupRequest request) {
        UserCommand.Create create = new UserCommand.Create(
                request.username(),
                request.email(),
                request.gender(),
                request.birth()
        );

        UserEntity saved = userService.save(create);

        return UserV1Dto.UserResponse.from(saved);
    }
}
