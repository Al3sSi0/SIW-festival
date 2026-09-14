package it.uniroma3.siw.film.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.film.model.Regista;
import it.uniroma3.siw.film.repository.RegistaRepository;

@Service
public class RegistaService {

    @Autowired
    private RegistaRepository registaRepository;

    @Transactional
    public Regista saveRegista(Regista regista) {
        return registaRepository.save(regista);
    }

    public boolean esisteRegista(String nome, String cognome) {
        return registaRepository.existsByNomeAndCognome(nome, cognome);
    }

    @Transactional(readOnly = true)
    public List<Regista> findAllRegisti() {
        return (List<Regista>) registaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Regista findRegistaById(Long id) {
        return registaRepository.findById(id).orElse(null);
    }

    @Transactional
    public void aggiornaRegista(Long id, Regista registaConNuoviDati) {
        Regista registaEsistente = registaRepository.findById(id).orElse(null);
        if (registaEsistente != null) {
            registaEsistente.setNome(registaConNuoviDati.getNome());
            registaEsistente.setCognome(registaConNuoviDati.getCognome());
            registaEsistente.setDataNascita(registaConNuoviDati.getDataNascita());
            registaEsistente.setNazionalita(registaConNuoviDati.getNazionalita());
            registaRepository.save(registaEsistente);
        }
    }

    public Long contaRegisti(){
        return registaRepository.count();
    }
}