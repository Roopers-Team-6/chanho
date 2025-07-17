package com.loopers.domain.user;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserEntity save(UserEntity user) {
        if (user == null || user.getId() == null) {
            throw new CoreException(ErrorType.BAD_REQUEST, "User or User ID cannot be null");
        }

        Long userId = user.getId();

        userRepository.findById(userId)
                .ifPresent(e -> {
                    throw new CoreException(ErrorType.CONFLICT, "User already exists with ID: [%s]".formatted(userId));
                });

        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new CoreException(ErrorType.CONFLICT, e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND, "User not found with ID: [%s]".formatted(id)));
    }
}
