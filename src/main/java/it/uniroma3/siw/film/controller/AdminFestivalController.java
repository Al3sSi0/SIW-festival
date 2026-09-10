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

import it.uniroma3.siw.film.model.Festival;
import it.uniroma3.siw.film.model.Film;
import it.uniroma3.siw.film.service.FestivalService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/festival") 
public class AdminFestivalController {

    @Autowired
    private FestivalService festivalService;

    @Autowired
    private it.uniroma3.siw.film.service.FilmService filmService;

    @GetMapping("/nuovo")
    public String formNuovoFestival(Model model) {
        model.addAttribute("festival", new Festival());
        return "admin/formNewFestival"; 
    }

    @PostMapping("/nuovo")
    public String salvaNuovoFestival(@Valid @ModelAttribute("festival") Festival festival, BindingResult bindingResult, Model model) {
        
        if (bindingResult.hasErrors()) {
            return "admin/formNewFestival";
        }

        festivalService.saveFestival(festival);
        
        return "redirect:/festival"; 
    }

    @GetMapping("/{id}/aggiungiFilm")
    public String scegliFilmDaAggiungere(@PathVariable("id") Long id, Model model) {
        model.addAttribute("festival", festivalService.findFestivalById(id));
        model.addAttribute("elencoFilm", filmService.findAllFilms());
        return "admin/addFilmToFestival";
    }

    @PostMapping("/{idFestival}/aggiungiFilm")
    public String salvaFilmNelFestival(@PathVariable("idFestival") Long idFestival, @org.springframework.web.bind.annotation.RequestParam("filmId") Long idFilm) {
            
        it.uniroma3.siw.film.model.Film film = filmService.findFilmById(idFilm);
            
        festivalService.aggiungiFilmAlFestival(idFestival, film);
            
        return "redirect:/festival/" + idFestival; 
    }

    @GetMapping("/modifica/{id}")
    public String formModificaFestival(@PathVariable("id") Long id, Model model) {
        model.addAttribute("festival", festivalService.findFestivalById(id));
        return "admin/formModificaFestival";
    }

    @PostMapping("/modifica/{id}")
    public String salvaFestivalModificato(@PathVariable("id") Long id, 
                                          @Valid @ModelAttribute("festival") Festival festival, 
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/formModificaFestival";
        }
        festivalService.aggiornaFestival(id, festival);
        return "redirect:/festival";
    }

    @GetMapping("/{idFestival}/rimuoviFilm/{idFilm}")
    public String rimuoviFilmDaFestival(@PathVariable("idFestival") Long idFestival, @PathVariable("idFilm") Long idFilm) {
        
        // 1. Recuperi il festival e il film usando i SERVICE (già iniettati in alto)
        Festival festival = festivalService.findFestivalById(idFestival);
        Film film = filmService.findFilmById(idFilm);
        
        // 2. Rimuovi il film dalla lista del festival
        festival.getFilmPartecipanti().remove(film); 
        // NOTA: Se la tua lista in Festival.java si chiama in un altro modo, cambia "getFilmInProgrammazione"
        
        // 3. Salvi il festival aggiornato sempre tramite il service
        festivalService.saveFestival(festival);
        
        // 4. Ritorni alla pagina dei dettagli del festival
        return "redirect:/festival/" + idFestival; 
    }

}
