package it.uniroma3.siw.film.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.film.model.Festival;

public interface FestivalRepository extends CrudRepository<Festival, Long> {
    List<Festival> findByFilmPartecipanti(it.uniroma3.siw.film.model.Film film);
}
