package com.loopers.interfaces.api.point;

import com.loopers.interfaces.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Point V1 API", description = "my-commerce 포인트 API 입니다.")
public interface PointV1ApiSpec {

    @Operation(summary = "포인트 조회")
    ApiResponse<PointV1Dto.PointResponse> balance(Long userId, HttpServletRequest request);

    @Operation(summary = "포인트 충전")
    ApiResponse<PointV1Dto.PointResponse> charge(Long userId, Long amount, HttpServletRequest request);

}
