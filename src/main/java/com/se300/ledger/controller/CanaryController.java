package com.se300.ledger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class CanaryController {
    @RequestMapping(value = "/html", method = RequestMethod.GET)
    public String sayHello(Model model) {
        model.addAttribute("date", new Date());
        return "example";
    }
    
    @RequestMapping(value = "/html", method = RequestMethod.POST)
    public String handlePostRequest(Model model) {
        model.addAttribute("date", new Date());
        return "example";
    }
}


