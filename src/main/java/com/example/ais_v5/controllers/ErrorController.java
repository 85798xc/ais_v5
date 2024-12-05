package com.example.ais_v5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // Return a custom error page (e.g., error.html)
        return "error";
    }
}
