package it.uniroma3.siw.film.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.film.model.Film;
import it.uniroma3.siw.film.service.FilmService;

@RestController
@RequestMapping("/api/film")
@CrossOrigin(origins = "http://localhost:3000") // Permette a React di leggere i dati
public class FilmRestController {

    @Autowired
    private FilmService filmService;


@GetMapping
    public Iterable<Film> getAllFilm() {
        return filmService.findAll(); 
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable("id") Long id) {
        return filmService.findFilmById(id);
    }
}