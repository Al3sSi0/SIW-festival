package it.uniroma3.siw.film.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.film.model.Festival;

public interface FestivalRepository extends CrudRepository<Festival, Long> {
    List<Festival> findByFilmPartecipanti(it.uniroma3.siw.film.model.Film film);

    boolean existsByNomeAndAnno(String nome, Integer anno);

    @Query("SELECT f FROM Festival f JOIN FETCH f.filmPartecipanti film JOIN FETCH film.regista WHERE f.id = :id")
    Festival findByIdConFilmERegisti(@Param("id") Long id);
}
