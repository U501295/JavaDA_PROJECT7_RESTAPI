package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.CurvePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class CurveController {
    // Inject Curve Point service
    @Autowired
    private CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        // find all Curve Point, add to model
        model.addAttribute("curvePoints", curvePointService.findAllCurvePoints());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePoint(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // check data valid and save to db, after saving return Curve list
        if (!result.hasErrors()) {
            curvePointService.saveCurvePoint(curvePoint);
            model.addAttribute("curvePoints", curvePointService.findAllCurvePoints());
            return "redirect:/curvePoint/list";
        }
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
    public String updateRating(@PathVariable("id") Long id, @Valid CurvePoint curvePoint,
                               BindingResult result, Model model) {
        //  check required fields, if valid call service to update Curve and return Curve list
        if (result.hasErrors()) {
            return "redirect:curvePoint/list";
        }
        curvePoint.setCurveId(id);
        curvePointService.saveCurvePoint(curvePoint);
        model.addAttribute("curvePoints", curvePointService.findAllCurvePoints());
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteRating(@PathVariable("id") Long id, Model model) {
        //  Find Curve by Id and delete the Curve, return to Curve list
        CurvePoint curvePointToDelete = curvePointService.findCurvePointById(id);
        curvePointService.deleteCurvePoint(curvePointToDelete.getCurveId());
        model.addAttribute("ratings", curvePointService.findAllCurvePoints());
        return "redirect:/curvePoint/list";
    }
}
