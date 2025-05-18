package com.yourorg.api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/monitor")
    public String monitor() {
        return "index";
    }
} 