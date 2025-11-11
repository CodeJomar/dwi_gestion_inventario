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

    @GetMapping("/ordenes")
    public String web2() {
       return "ordenes";
    }
    
    @GetMapping("/productos")
    public String web3() {
        return "productos";
    }
    
    @GetMapping("/reportes")
    public String web4() {
        return "reportes";
    }

    @GetMapping("/perfil")
    public String web5() { return "perfil"; }
}
