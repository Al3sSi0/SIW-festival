package it.uniroma3.siw.film.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.film.model.Regista;

public interface RegistaRepository extends CrudRepository<Regista, Long> {
    boolean existsByNomeAndCognome(String nome, String cognome);

    //@Query("select r from Regista r order by r.nome")
    //List<Regista>findAll();

}