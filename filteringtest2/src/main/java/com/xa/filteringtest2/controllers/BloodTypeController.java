package com.xa.filteringtest2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="bloodtype")
public class BloodTypeController {
    

    @GetMapping(value="index")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("/bloodtype/index");
        return view;
    }

}
