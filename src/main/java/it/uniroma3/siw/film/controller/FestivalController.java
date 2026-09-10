package it.uniroma3.siw.film.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.film.service.FestivalService;

@Controller
public class FestivalController {

    @Autowired
    private FestivalService festivalService;

    @GetMapping("/festival")
    public String mostraElencoFestival(Model model) {
        model.addAttribute("elencoFestival", festivalService.findAllFestivals());
        return "festival";
    }

    @GetMapping("/festival/{id}")
    public String mostraDettagliFestival(@PathVariable("id") Long id, Model model) {
        model.addAttribute("festival", festivalService.findFestivalById(id));
        return "festivalDettagli";
    }

}