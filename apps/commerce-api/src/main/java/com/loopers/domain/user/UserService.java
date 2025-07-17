package com.loopers.domain.user;

import com.loopers.application.user.UserCommand;

import java.util.Optional;

public interface UserService {

    UserEntity save(UserCommand.Create userCreateCommand);

    Optional<UserEntity> findById(Long id);
}
