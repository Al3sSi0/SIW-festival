package it.uniroma3.siw.film.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.film.model.Film;
import it.uniroma3.siw.film.model.Proiezione;
import it.uniroma3.siw.film.model.Sala;

public interface ProiezioneRepository extends CrudRepository<Proiezione, Long> {
    
    List<Proiezione> findBySalaAndData(Sala sala, LocalDate data);

    List<Proiezione> findByFilm(Film film);
}