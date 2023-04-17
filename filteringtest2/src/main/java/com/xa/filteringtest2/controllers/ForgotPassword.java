package com.xa.filteringtest2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/forgotpassword")
public class ForgotPassword {

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("/register/forgotpassword");
        return view;
    }

}
