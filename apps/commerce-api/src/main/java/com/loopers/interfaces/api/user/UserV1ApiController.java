package com.loopers.interfaces.api.user;

import com.loopers.interfaces.api.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserV1ApiController implements UserV1ApiSpec {

    @PostMapping
    @Override
    public ApiResponse<UserV1Dto.UserResponse> signUp(
            @RequestBody UserV1Dto.SignupRequest request) {
        return ApiResponse.success(new UserV1Dto.UserResponse(
                1L,
                request.name(),
                request.email(),
                request.gender(),
                request.birth()
        ));
    }

}
