package com.loopers.domain.point;

public interface PointService {

    PointEntity save(PointEntity point);

    PointEntity findByUserId(Long id);

}
