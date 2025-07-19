package com.loopers.domain.user;

import java.util.Optional;

public interface UserRepository {

    UserEntity save(UserEntity userEntity);

    Optional<UserEntity> findById(Long id);

}
