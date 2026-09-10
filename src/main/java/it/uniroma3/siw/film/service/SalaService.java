package it.uniroma3.siw.film.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.film.model.Sala;
import it.uniroma3.siw.film.repository.SalaRepository;

@Service
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;

    @Transactional
    public void saveSala(Sala sala) {
        salaRepository.save(sala);
    }

    public Sala findSalaById(Long id) {
        return salaRepository.findById(id).orElse(null);
    }

    public List<Sala> findAllSale() {
        return (List<Sala>) salaRepository.findAll();
    }

    @Transactional
    public void aggiornaSala(Long id, Sala salaConNuoviDati) {
        Sala salaEsistente = salaRepository.findById(id).orElse(null);
        
        if (salaEsistente != null) {
            salaEsistente.setNome(salaConNuoviDati.getNome());
            salaEsistente.setCapienza(salaConNuoviDati.getCapienza());

            salaRepository.save(salaEsistente);
        }
    }
}