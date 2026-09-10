package it.uniroma3.siw.film.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siw.film.model.Film;
import it.uniroma3.siw.film.model.Proiezione;
import it.uniroma3.siw.film.service.FestivalService;
import it.uniroma3.siw.film.service.FilmService;
import it.uniroma3.siw.film.service.ProiezioneService;
import it.uniroma3.siw.film.service.SalaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/proiezione")
public class AdminProiezioneController {

    @Autowired
    private ProiezioneService proiezioneService;
    @Autowired
    private FestivalService festivalService;
    @Autowired
    private FilmService filmService;
    @Autowired
    private SalaService salaService;

    @GetMapping("/nuova")
    public String formNuovaProiezione(Model model) {
        model.addAttribute("proiezione", new Proiezione());
        model.addAttribute("festivals", festivalService.findAllFestivals());
        model.addAttribute("films", filmService.findAllFilms()); 
        model.addAttribute("sale", salaService.findAllSale());
        return "admin/formNewProiezione";
    }

    @PostMapping("/nuova")
    public String salvaNuovaProiezione(@Valid @ModelAttribute("proiezione") Proiezione proiezione, 
                                       BindingResult bindingResult, 
                                       Model model) {
        if (bindingResult.hasErrors()) {
            ricaricaListePerForm(model);
            return "admin/formNewProiezione";
        }

        Film filmCompleto = filmService.findFilmById(proiezione.getFilm().getId());

        boolean isLibera = proiezioneService.verificaDisponibilitaSala(
                proiezione.getSala(), 
                proiezione.getData(), 
                proiezione.getOra(), 
                filmCompleto.getDurata()
        );

        if (!isLibera) {
            bindingResult.rejectValue("ora", "error.proiezione", "ATTENZIONE: La sala selezionata è già occupata in questo orario!");
            ricaricaListePerForm(model);
            return "admin/formNewProiezione";
        }

        proiezioneService.salvaProiezione(proiezione);
        return "redirect:/";
    }

    private void ricaricaListePerForm(Model model) {
        model.addAttribute("festivals", festivalService.findAllFestivals());
        model.addAttribute("films", filmService.findAllFilms());
        model.addAttribute("sale", salaService.findAllSale());
    }

    // --- ECCO I METODI CORRETTI CON {festivalId} NEL PERCORSO ---

    // 1. ELIMINA PROIEZIONE
    @GetMapping("/cancella/{festivalId}/{proiezioneId}")
    public String cancellaProiezione(@PathVariable("festivalId") Long festivalId, 
                                     @PathVariable("proiezioneId") Long proiezioneId) {
        proiezioneService.deleteById(proiezioneId);
        
        // Corretto il redirect a /festival/
        return "redirect:/festival/" + festivalId;
    }

    // 2. MOSTRA IL FORM DI MODIFICA
    @GetMapping("/modifica/{festivalId}/{proiezioneId}")
    public String formModificaProiezione(@PathVariable("festivalId") Long festivalId, 
                                         @PathVariable("proiezioneId") Long proiezioneId, 
                                         Model model) {
        model.addAttribute("proiezione", proiezioneService.findById(proiezioneId));
        model.addAttribute("festivalId", festivalId); 
        return "admin/formModificaProiezione";
    }

    // 3. SALVA LE MODIFICHE
    @PostMapping("/modifica/{festivalId}/{proiezioneId}")
    public String salvaModificaProiezione(@PathVariable("festivalId") Long festivalId,
                                          @PathVariable("proiezioneId") Long proiezioneId,
                                          @ModelAttribute("proiezione") Proiezione proiezione) {
        
        proiezioneService.aggiornaProiezione(proiezioneId, proiezione);
        
        return "redirect:/festival/" + festivalId;
    }
}