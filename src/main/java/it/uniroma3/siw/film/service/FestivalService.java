package it.uniroma3.siw.film.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.film.model.Festival;
import it.uniroma3.siw.film.repository.FestivalRepository;

@Service
public class FestivalService {

    @Autowired
    private FestivalRepository festivalRepository;

    @Transactional
    public Festival saveFestival(Festival festival) {
        return festivalRepository.save(festival);
    }

    @Transactional(readOnly = true)
    public List<Festival> findAllFestivals() {
        return (List<Festival>) festivalRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Festival findFestivalById(Long id) {
        return festivalRepository.findById(id).orElse(null);
    }

    @Transactional
    public void aggiungiFilmAlFestival(Long idFestival, it.uniroma3.siw.film.model.Film film) {
        Festival festival = festivalRepository.findById(idFestival).orElse(null);
        if (festival != null) {
            festival.getFilmPartecipanti().add(film);
            festivalRepository.save(festival);
        }
    }

    @Transactional
    public void aggiornaFestival(Long id, Festival festivalConNuoviDati) {
        Festival festivalEsistente = festivalRepository.findById(id).orElse(null);
        if (festivalEsistente != null) {
            festivalEsistente.setNome(festivalConNuoviDati.getNome());
            festivalEsistente.setAnno(festivalConNuoviDati.getAnno());
            festivalEsistente.setCitta(festivalConNuoviDati.getCitta());
            festivalEsistente.setDataInizio(festivalConNuoviDati.getDataInizio());
            festivalEsistente.setDataFine(festivalConNuoviDati.getDataFine());
            festivalEsistente.setDescrizione(festivalConNuoviDati.getDescrizione());
            festivalRepository.save(festivalEsistente);
        }
    }

    @Transactional(readOnly = true)
    public List<Festival> findByFilm(it.uniroma3.siw.film.model.Film film) {
        return festivalRepository.findByFilmPartecipanti(film);
    }
}