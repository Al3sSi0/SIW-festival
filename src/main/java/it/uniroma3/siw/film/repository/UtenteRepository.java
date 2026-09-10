package it.uniroma3.siw.film.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.film.model.Utente;

public interface UtenteRepository extends CrudRepository<Utente, Long> {
    
    Optional<Utente> findByUsername(String username);
}
