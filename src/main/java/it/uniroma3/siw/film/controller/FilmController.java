package it.uniroma3.siw.film.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.film.model.Film;
import it.uniroma3.siw.film.service.FilmService;
import it.uniroma3.siw.film.service.ProiezioneService;
import it.uniroma3.siw.film.service.RecensioneService;
import it.uniroma3.siw.film.service.FestivalService; 

@Controller
public class FilmController {

    @Autowired private FilmService filmService;
    @Autowired private RecensioneService recensioneService;
    @Autowired private ProiezioneService proiezioneService;
    
    @Autowired private FestivalService festivalService; 

    @GetMapping("/film")
    public String mostraTuttiIFilm(Model model) {
        model.addAttribute("numfilm",filmService.contaNum());
        model.addAttribute("films", filmService.findAllFilms());
        return "filmList"; 
    }

    @GetMapping("/film/{id}")
    public String mostraDettagliFilm(@PathVariable("id") Long id, Model model) {
        // 1. Troviamo il film
        Film film = filmService.findFilmById(id);
        model.addAttribute("film", film);
        
        // 2. Cerchiamo le recensioni e le proiezioni legate a questo film
        model.addAttribute("recensioni", recensioneService.findByFilm(film));
        model.addAttribute("proiezioni", proiezioneService.findByFilm(film));
        
        // 3. ECCO LA RIGA DEI FESTIVAL CHE MANCAVA!
        model.addAttribute("festivals", festivalService.findByFilm(film));
        
        return "filmDettagli";
    }
}