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

import it.uniroma3.siw.film.model.Regista;
import it.uniroma3.siw.film.service.RegistaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/regista")
public class AdminRegistaController {

    @Autowired
    private RegistaService registaService;

    @GetMapping("/nuovo")
    public String formNuovoRegista(Model model) {
        model.addAttribute("regista", new Regista());
        return "admin/formNewRegista";
    }

@PostMapping("/nuovo")
public String salvaNuovoRegista(@Valid @ModelAttribute("regista") Regista regista, BindingResult bindingResult, Model model) {
    
    if (registaService.esisteRegista(regista.getNome(), regista.getCognome())) {
        bindingResult.rejectValue("nome", "error.regista", "Un regista con questo nome e cognome esiste già.");
    }

    if (bindingResult.hasErrors()) {
        return "admin/formNewRegista";
    }

    registaService.saveRegista(regista);
    return "redirect:/regista"; 
}

    @GetMapping("/modifica/{id}")
    public String formModificaRegista(@PathVariable("id") Long id, Model model) {
        model.addAttribute("regista", registaService.findRegistaById(id));
        return "admin/formModificaRegista";
    }

    @PostMapping("/modifica/{id}")
    public String salvaRegistaModificato(@PathVariable("id") Long id, 
                                         @Valid @ModelAttribute("regista") Regista regista, 
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/formModificaRegista";
        }
        registaService.aggiornaRegista(id, regista);
        return "redirect:/";
    }
}