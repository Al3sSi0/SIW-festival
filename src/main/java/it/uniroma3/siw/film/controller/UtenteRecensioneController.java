package it.uniroma3.siw.film.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siw.film.model.Recensione;
import it.uniroma3.siw.film.service.FilmService;
import it.uniroma3.siw.film.service.RecensioneService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/utente/recensione")
public class UtenteRecensioneController {

    @Autowired
    private RecensioneService recensioneService;
    @Autowired
    private FilmService filmService;

    @GetMapping("/nuova/{filmId}")
    public String formNuovaRecensione(@PathVariable("filmId") Long filmId, Model model) {
        model.addAttribute("film", filmService.findFilmById(filmId));
        model.addAttribute("recensione", new Recensione());
        return "utente/formNewRecensione";
    }

    @PostMapping("/nuova/{filmId}")
    public String salvaRecensione(@PathVariable("filmId") Long filmId,
                                  @Valid @ModelAttribute("recensione") Recensione recensione,
                                  BindingResult bindingResult,
                                  Principal principal,
                                  Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("film", filmService.findFilmById(filmId));
            return "utente/formNewRecensione";
        }

        String usernameLoggato = principal.getName();

        recensioneService.aggiungiRecensioneAFilm(recensione, filmId, usernameLoggato);

        return "redirect:/film/" + filmId; 
    }

    @GetMapping("/cancella/{id}")
    public String cancellaRecensione(@PathVariable("id") Long id, Principal principal) {
        String usernameLoggato = principal.getName();
        
        Recensione recensione = recensioneService.findById(id);
        if (recensione == null) {
            return "redirect:/";
        }
        
        Long idFilm = recensione.getFilm().getId();
        
        recensioneService.cancellaSuaRecensione(id, usernameLoggato);
        
        return "redirect:/film/" + idFilm;
    }

    @GetMapping("/modifica/{id}")
    public String formModificaRecensione(@PathVariable("id") Long id, Principal principal, Model model) {
        Recensione recensione = recensioneService.findById(id);
        
        // Sicurezza: se la recensione non esiste o non è sua, lo rimandiamo alla home!
        if (recensione == null || !recensione.getAutore().getUsername().equals(principal.getName())) {
            return "redirect:/";
        }
        
        model.addAttribute("recensione", recensione);
        model.addAttribute("film", recensione.getFilm());
        return "utente/formModificaRecensione";
    }

    @PostMapping("/modifica/{id}")
    public String aggiornaRecensione(@PathVariable("id") Long id,
                                     @Valid @ModelAttribute("recensione") Recensione recensione,
                                     BindingResult bindingResult,
                                     Principal principal,
                                     Model model) {
        
        Recensione recensioneOriginale = recensioneService.findById(id);
        if (recensioneOriginale == null) return "redirect:/";

        if (bindingResult.hasErrors()) {
            model.addAttribute("film", recensioneOriginale.getFilm());
            return "utente/formModificaRecensione";
        }

        // Passiamo l'id, i nuovi dati e chi sta facendo la richiesta
        recensioneService.aggiornaSuaRecensione(id, recensione, principal.getName());

        return "redirect:/film/" + recensioneOriginale.getFilm().getId();
    }
}