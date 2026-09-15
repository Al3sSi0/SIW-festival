package it.uniroma3.siw.film.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.film.model.Film;
import it.uniroma3.siw.film.model.Regista;
import it.uniroma3.siw.film.repository.FilmRepository;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Transactional
    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }

    @Transactional(readOnly = true)
    public List<Film> findAllFilms() {
        return (List<Film>) filmRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Film findFilmById(Long id) {
        return filmRepository.findById(id).orElse(null); 
    }

    public boolean esisteFilm(String titolo, Integer anno) {
        return filmRepository.existsByTitoloAndAnno(titolo, anno);
    }


    @Transactional 
    public void aggiornaFilm(Long id, it.uniroma3.siw.film.model.Film filmConNuoviDati) {
        it.uniroma3.siw.film.model.Film filmEsistente = filmRepository.findById(id).orElse(null);
        
        if (filmEsistente != null) {
            filmEsistente.setTitolo(filmConNuoviDati.getTitolo());
            filmEsistente.setAnno(filmConNuoviDati.getAnno());
            filmEsistente.setGenere(filmConNuoviDati.getGenere());
            filmEsistente.setRegista(filmConNuoviDati.getRegista());
            
            filmRepository.save(filmEsistente);
        }
    }

    @Transactional
    public Iterable<Film> findAll() {
        return filmRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Film> findByRegista(Regista regista) {
        return filmRepository.findByRegista(regista);
    }

    public Long contaFilm(){
        return filmRepository.count();
    }
}