package com.nnk.springboot.controllers.api;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : JULIEN BARONI
 *
 * <p>
 * Controller permettant d'atteindre les URLs en lien avec les entités curvePoint dans l'API.
 * <p>
 */
@Slf4j
@RestController
@RequestMapping("/api/curve")

public class CurvePointRestController {
    @Autowired
    private CurvePointService curvePointService;


    @GetMapping()
    public List<CurvePoint> getCurvePoints() {
        log.debug("get all curves");
        return curvePointService.findAllCurvePoints();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCurvePoint(@PathVariable Long id) {
        try {
            CurvePoint curveEntity = curvePointService.findCurvePointById(id);
            log.debug("successfully get curve/" + id);
            return ResponseEntity.status(HttpStatus.OK).body(curveEntity);
        } catch (IllegalArgumentException e) {
            String logAndBodyMessage = "error while getting curve because of missing curve with id=" + id;
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(logAndBodyMessage);
        }
    }

    @PostMapping()
    public ResponseEntity<?> postCurvePoint(@Valid @RequestBody CurvePoint curveEntity,
                                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String logAndBodyMessage = "error while posting curve because of wrong input data : "
                    + bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).
                    collect(Collectors.joining(", "));
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(logAndBodyMessage);
        }

        CurvePoint curveEntitySaved = curvePointService.saveCurvePoint(curveEntity);
        log.debug("successfully post curve");
        return ResponseEntity.status(HttpStatus.CREATED).body(curveEntitySaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putCurvePoint(@PathVariable Long id,
                                           @Valid @RequestBody CurvePoint curveEntity,
                                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String logAndBodyMessage = "error while putting curve because of wrong input data : "
                    + bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).
                    collect(Collectors.joining(", "));
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(logAndBodyMessage);
        }

        try {
            CurvePoint curveEntityToModify = curvePointService.findCurvePointById(id);
            curveEntityToModify = curveEntity;
            curveEntityToModify.setCurveId(id);
            CurvePoint curveSaved = curvePointService.saveCurvePoint(curveEntity);
            log.debug("successfully put curve/" + id);
            return ResponseEntity.status(HttpStatus.OK).body(curveSaved);
        } catch (IllegalArgumentException e) {
            String logAndBodyMessage = "error while putting curve because missing curve with id=" + id;
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(logAndBodyMessage);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCurvePoint(@PathVariable Long id) {
        try {
            curvePointService.deleteCurvePoint(id);
            String logAndBodyMessage = "successfully delete curve/" + id;
            log.debug(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.OK).body(logAndBodyMessage);
        } catch (IllegalArgumentException e) {
            String logAndBodyMessage = "error while deleting curve because of missing curve with id=" + id;
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(logAndBodyMessage);
        }
    }
}
