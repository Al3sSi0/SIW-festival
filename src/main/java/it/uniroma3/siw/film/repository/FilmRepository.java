package it.uniroma3.siw.film.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.film.model.Film;
import it.uniroma3.siw.film.model.Regista;
import jakarta.persistence.QueryHint;

public interface FilmRepository extends CrudRepository<Film, Long> {
    // Se in futuro vorrai cercare un film per titolo (Bonus facoltativo), ti basterà aggiungere:
    // Iterable<Film> findByTitolo(String titolo);
    List<Film> findByRegista(Regista regista);

    boolean existsByTitoloAndAnno(String titolo, Integer anno);

    @Query("select count(f) from Film f")
    Long contafilm();
}
