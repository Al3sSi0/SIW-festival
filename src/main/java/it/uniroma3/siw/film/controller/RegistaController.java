package it.uniroma3.siw.film.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.film.model.Regista;
import it.uniroma3.siw.film.service.FilmService;
import it.uniroma3.siw.film.service.RegistaService;

@Controller
public class RegistaController {

    @Autowired
    private RegistaService registaService;
    
    @Autowired
    private FilmService filmService;

    @GetMapping("/regista")
    public String mostraElencoRegisti(Model model) {
        model.addAttribute("elencoRegisti", registaService.findAllRegisti());
        return "elencoRegisti";
    }

    @GetMapping("/regista/{id}")
    public String mostraDettagliRegista(@PathVariable("id") Long id, Model model) {
        Regista regista = registaService.findRegistaById(id);
        model.addAttribute("regista", regista);
        
        model.addAttribute("filmDiretti", filmService.findByRegista(regista));
        
        return "registaDettagli";
    }
}