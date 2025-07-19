package com.loopers.interfaces.api.point;

import com.loopers.application.user.UserFacade;
import com.loopers.interfaces.api.ApiResponse;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/points")
@RequiredArgsConstructor
public class PointV1ApiController implements PointV1ApiSpec {
    private final UserFacade userFacade;

    @GetMapping("/{targetUserId}")
    @Override
    public ApiResponse<PointV1Dto.PointResponse> balance(@PathVariable Long targetUserId, HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
        if (userId == null) {
            throw new CoreException(ErrorType.BAD_REQUEST, "X-USER-ID header is required.");
        }

        return ApiResponse.success(
                userFacade.findBalance(targetUserId)
        );
    }

    @PostMapping
    @Override
    public ApiResponse<PointV1Dto.PointResponse> charge(
            @RequestBody PointV1Dto.ChargeRequest chargeRequest, HttpServletRequest request
    ) {
        String userId = request.getHeader("X-USER-ID");
        if (userId == null) {
            throw new CoreException(ErrorType.BAD_REQUEST, "X-USER-ID header is required.");
        }

        return ApiResponse.success(
                userFacade.charge(chargeRequest.userId(), chargeRequest.amount())
        );
    }
}
