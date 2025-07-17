package com.loopers.interfaces.api.point;

import com.loopers.domain.point.PointEntity;

public class PointV1Dto {
    public record PointResponse(
            Long id,
            Long amount
    ) {
        public static PointResponse from(PointEntity point) {
            return new PointResponse(
                    point.getId(),
                    point.getAmount()
            );
        }
    }
}
