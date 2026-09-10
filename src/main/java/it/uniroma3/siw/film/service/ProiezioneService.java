package it.uniroma3.siw.film.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.film.model.Film;
import it.uniroma3.siw.film.model.Proiezione;
import it.uniroma3.siw.film.model.Sala;
import it.uniroma3.siw.film.repository.ProiezioneRepository;

@Service
public class ProiezioneService {

    @Autowired
    private ProiezioneRepository proiezioneRepository;

    public boolean verificaDisponibilitaSala(Sala sala, LocalDate data, LocalTime oraInizio, Integer durataFilmMinuti) {
        LocalTime oraFine = oraInizio.plusMinutes(durataFilmMinuti);

        List<Proiezione> proiezioniDelGiorno = proiezioneRepository.findBySalaAndData(sala, data);

        for (Proiezione pEsistente : proiezioniDelGiorno) {
            LocalTime inizioEsistente = pEsistente.getOra();
            LocalTime fineEsistente = inizioEsistente.plusMinutes(pEsistente.getFilm().getDurata());

            if (oraInizio.isBefore(fineEsistente) && oraFine.isAfter(inizioEsistente)) {
                return false; 
            }
        }
        return true; 
    }

    @Transactional(readOnly = true)
    public List<Proiezione> findByFilm(Film film) {
        return proiezioneRepository.findByFilm(film);
    }

    @Transactional
    public void salvaProiezione(Proiezione proiezione) {
        proiezioneRepository.save(proiezione);
    }

    public boolean isSovrapposta(Proiezione nuovaProiezione) {
    List<Proiezione> proiezioniGiorno = proiezioneRepository
        .findBySalaAndData(nuovaProiezione.getSala(), nuovaProiezione.getData());
        
    LocalTime inizioNuova = nuovaProiezione.getOra();
    LocalTime fineNuova = inizioNuova.plusMinutes(nuovaProiezione.getFilm().getDurata());

    for (Proiezione esistente : proiezioniGiorno) {
        LocalTime inizioEsistente = esistente.getOra();
        LocalTime fineEsistente = inizioEsistente.plusMinutes(esistente.getFilm().getDurata());

        if (inizioNuova.isBefore(fineEsistente) && fineNuova.isAfter(inizioEsistente)) {
            return true; 
        }
    }
    
    return false;
}

public Proiezione findById(Long id) {
        return proiezioneRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(Long id) {
        proiezioneRepository.deleteById(id);
    }

    @Transactional
    public void aggiornaProiezione(Long id, Proiezione nuoviDati) {
        Proiezione p = proiezioneRepository.findById(id).orElse(null);
        if (p != null) {
            p.setData(nuoviDati.getData());
            p.setOra(nuoviDati.getOra());
            p.setStato(nuoviDati.getStato());
            
            proiezioneRepository.save(p);
        }
    }
    
}