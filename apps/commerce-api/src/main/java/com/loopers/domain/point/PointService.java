package com.loopers.domain.point;

import java.util.Optional;

public interface PointService {

    PointEntity save(PointEntity point);

    Optional<PointEntity> findByUserId(Long id);

}
