package com.multisupply.sgi.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class WebController {

    @GetMapping("/dashboard")
    public String web1() {
        return "dashboard";
    }

    @GetMapping("/reportes")
    public String web4() {
        return "reportes";
    }

}