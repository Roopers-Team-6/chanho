package com.loopers.domain.user;

public interface UserService {

    UserEntity save(UserEntity user);

    UserEntity findById(Long id);
}
