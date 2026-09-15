package it.uniroma3.siw.film.controller;

import it.uniroma3.siw.film.repository.FilmRepository;
import it.uniroma3.siw.film.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;
@Controller
public class HomeController {


    private final FilmService filmService;
    private final FilmRepository filmRepository;

    HomeController(FilmRepository filmRepository, FilmService filmService) {
        this.filmRepository = filmRepository;
        this.filmService = filmService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("numFilm", filmService.contaFilm());
        return "index"; 
    }

    @GetMapping("/login")
        public String mostraFormLogin() {
    return "login";
}
}