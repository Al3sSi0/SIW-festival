package it.uniroma3.siw.film.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siw.film.model.Film;
import it.uniroma3.siw.film.service.FilmService;
import it.uniroma3.siw.film.service.RegistaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/film")
public class AdminFilmController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private RegistaService registaService;

    @GetMapping("/nuovo")
    public String formNuovoFilm(Model model) {
        model.addAttribute("film", new Film());
        model.addAttribute("elencoRegisti", registaService.findAllRegisti());
        return "admin/formNewFilm";
    }

    @PostMapping("/nuovo")
    public String salvaNuovoFilm(@Valid @ModelAttribute("film") Film film, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            
            model.addAttribute("elencoRegisti", registaService.findAllRegisti());
            return "admin/formNewFilm";
        }
        filmService.saveFilm(film);
        return "redirect:/"; 
    }

    @GetMapping("/modifica/{id}")
    public String formModificaFilm(@PathVariable("id") Long id, Model model) {
        Film filmDaModificare = filmService.findFilmById(id);
        
        model.addAttribute("film", filmDaModificare);
        
        model.addAttribute("elencoRegisti", registaService.findAllRegisti());
        
        return "admin/formModificaFilm";
    }

    @PostMapping("/modifica/{id}")
    public String salvaFilmModificato(@PathVariable("id") Long id, 
                                      @Valid @ModelAttribute("film") Film film, 
                                      BindingResult bindingResult, 
                                      Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("elencoRegisti", registaService.findAllRegisti());
            return "admin/formModificaFilm";
        }
        
        filmService.aggiornaFilm(id, film);
        return "redirect:/"; 
    }
}