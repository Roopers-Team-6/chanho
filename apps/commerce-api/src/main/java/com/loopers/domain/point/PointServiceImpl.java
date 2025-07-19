package com.loopers.domain.point;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;

    @Override
    public PointEntity save(PointEntity point) {
        try {
            return pointRepository.save(point);
        } catch (DataIntegrityViolationException e) {
            throw new CoreException(ErrorType.CONFLICT, e.getMessage());
        }
    }

    @Override
    public PointEntity findByUserId(Long id) {
        return pointRepository.findByUserId(id)
                .orElse(null);
    }
}
