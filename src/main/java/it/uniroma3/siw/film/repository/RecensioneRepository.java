package it.uniroma3.siw.film.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.film.model.Film;
import it.uniroma3.siw.film.model.Recensione;

public interface RecensioneRepository extends CrudRepository<Recensione, Long> {
    
    List<Recensione> findByFilm(Film film);
}