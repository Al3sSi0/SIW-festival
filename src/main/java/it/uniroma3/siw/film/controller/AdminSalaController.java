package it.uniroma3.siw.film.controller;

import it.uniroma3.siw.film.model.Sala;
import it.uniroma3.siw.film.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/sala")
public class AdminSalaController {

    @Autowired
    private SalaService salaService;

    @GetMapping("/nuova")
    public String formNuovaSala(Model model) {
        model.addAttribute("sala", new Sala());
        return "admin/formNewSala";
    }

    @PostMapping("/nuova")
    public String salvaNuovaSala(@Valid @ModelAttribute("sala") Sala sala, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/formNewSala";
        }
        salaService.saveSala(sala); 
        return "redirect:/";
    }

    @GetMapping("/modifica/{id}")
    public String formModificaSala(@PathVariable("id") Long id, Model model) {
        model.addAttribute("sala", salaService.findSalaById(id));
        return "admin/formModificaSala";
    }

    @PostMapping("/modifica/{id}")
    public String salvaSalaModificata(@PathVariable("id") Long id, 
        @Valid @ModelAttribute("sala") Sala sala, 
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/formModificaSala";
        }
        salaService.aggiornaSala(id, sala);
        return "redirect:/";
    }
}