package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : JULIEN BARONI
 *
 * <p>
 * Service permettant d'effectuer les actions CRUDs sur les entités curvePoint dans l'application.
 * <p>
 */
@Slf4j
@Service
public class CurvePointService {
    @Autowired
    private CurvePointRepository curvePointRepository;

    public List<CurvePoint> findAllCurvePoints() {
        List<CurvePoint> curvePoints = curvePointRepository.findAll();
        log.debug("curvePoint : get all");
        return curvePoints;
    }

    public CurvePoint saveCurvePoint(CurvePoint curvePoint) {
        log.debug("curvePoint : save");
        return curvePointRepository.save(curvePoint);
    }

    public CurvePoint findCurvePointById(Long id) {
        log.debug("curvePoint : find by id");
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
        return curvePoint;
    }

    public void deleteCurvePoint(Long id) {
        log.debug("curvePoint : delete");
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
        curvePointRepository.delete(curvePoint);

    }
}
