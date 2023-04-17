package com.xa.filteringtest2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="pasien")
public class PasienController {
    
    // @Autowired private CategoryRepository categoryRepo;

    @GetMapping(value="index")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("/pasien/index");
        // List<Category> category = this.categoryRepo.findAll();
        // view.addObject("categorydata", category);
        return view;
    }

}
