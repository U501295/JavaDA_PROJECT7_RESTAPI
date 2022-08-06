package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * @author : JULIEN BARONI
 *
 * <p>
 * Controller permettant d'atteindre les URLs en lien avec les entités curvePoint dans l'application.
 * <p>
 */
@Slf4j
@Controller
public class CurveController {
    @Autowired
    private CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", curvePointService.findAllCurvePoints());
        log.debug("curvePoint : affichage de la liste");
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePoint(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            curvePointService.saveCurvePoint(curvePoint);
            model.addAttribute("curvePoints", curvePointService.findAllCurvePoints());
            log.debug("curvePoint : erreur dans l'ajout d'un curvePoint");
            return "redirect:/curvePoint/list";
        }
        log.debug("curvePoint : success dans l'ajout d'un curvePoint");
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        //  get CurvePoint by Id and to model then show to the form
        CurvePoint curvePoint = curvePointService.findCurvePointById(id);
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(@PathVariable("id") Long id, @Valid CurvePoint curvePoint,
                              BindingResult result, Model model) {

        if (result.hasErrors()) {
            log.debug("curvePoint : erreur dans la modification d'un curvePoint");
            return "redirect:/curvePoint/list";
        }
        curvePoint.setCurveId(id);
        curvePointService.saveCurvePoint(curvePoint);
        model.addAttribute("curvePoints", curvePointService.findAllCurvePoints());
        log.debug("curvePoint : success dans la modification d'un curvePoint");
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") Long id, Model model) {
        CurvePoint curvePointToDelete = curvePointService.findCurvePointById(id);
        curvePointService.deleteCurvePoint(curvePointToDelete.getCurveId());
        model.addAttribute("ratings", curvePointService.findAllCurvePoints());
        log.debug("curvePoint : success dans la suppression d'un curvePoint");
        return "redirect:/curvePoint/list";
    }
}
