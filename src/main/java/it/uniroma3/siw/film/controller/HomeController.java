package it.uniroma3.siw.film.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; 
    }

    @GetMapping("/login")
        public String mostraFormLogin() {
    return "login";
}
}