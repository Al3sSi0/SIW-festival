package it.uniroma3.siw.film.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.film.model.Film;
import it.uniroma3.siw.film.model.Recensione;
import it.uniroma3.siw.film.model.Utente;
import it.uniroma3.siw.film.repository.FilmRepository;
import it.uniroma3.siw.film.repository.RecensioneRepository;
import it.uniroma3.siw.film.repository.UtenteRepository;

@Service
public class RecensioneService {

    @Autowired
    private RecensioneRepository recensioneRepository;
    
    @Autowired
    private FilmRepository filmRepository;
    
    @Autowired
    private UtenteRepository utenteRepository;

    @Transactional(readOnly = true)
    public Recensione findById(Long id) {
        return recensioneRepository.findById(id).orElse(null);
    }

@Transactional
    public boolean aggiungiRecensioneAFilm(Recensione recensione, Long filmId, String usernameAutore) {
        Film film = filmRepository.findById(filmId).orElse(null);
        Utente autore = utenteRepository.findByUsername(usernameAutore).orElse(null);

        if (film != null && autore != null) {
            if (recensioneRepository.existsByAutoreAndFilm(autore, film)) {
                return false;
            }

            recensione.setFilm(film);
            recensione.setAutore(autore);
            recensioneRepository.save(recensione);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<Recensione> findByFilm(Film film) {
        return recensioneRepository.findByFilm(film);
    }

    @Transactional
    public void cancellaSuaRecensione(Long idRecensione, String usernameRichiedente) {
        Recensione recensione = recensioneRepository.findById(idRecensione).orElse(null);
        
        if (recensione != null && recensione.getAutore().getUsername().equals(usernameRichiedente)) {
            recensioneRepository.delete(recensione);
        }
    }

    @Transactional
    public void aggiornaSuaRecensione(Long idRecensione, Recensione nuoviDati, String usernameRichiedente) {
        Recensione recensione = recensioneRepository.findById(idRecensione).orElse(null);
        
        if (recensione != null && recensione.getAutore().getUsername().equals(usernameRichiedente)) {
            recensione.setVoto(nuoviDati.getVoto());
            recensione.setTesto(nuoviDati.getTesto());
            recensioneRepository.save(recensione);
        }
    }
}