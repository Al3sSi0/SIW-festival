package it.uniroma3.siw.film.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.film.model.Sala;

public interface SalaRepository extends CrudRepository<Sala, Long> {
    boolean existsByNome(String nome);
}
