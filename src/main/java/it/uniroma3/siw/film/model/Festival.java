package it.uniroma3.siw.film.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Festival {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Il nome non può essere vuoto")
    private String nome;
    @NotNull(message = "L'anno è obbligatorio")
    @Max(value = 2026, message = "L'anno deve essere minore del 2026")
    private Integer anno;
    @NotBlank(message = "La città è obbligatoria")
    private String citta;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private String descrizione;

    @OneToMany(mappedBy = "festival")
    private List<Proiezione> proiezioni;

    @ManyToMany
    private List<Film> filmPartecipanti = new ArrayList<>();

    public Festival() {
    }


    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnno() {
        return anno;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public List<Film> getFilmPartecipanti() {
        return filmPartecipanti;
    }

    public void setFilmPartecipanti(List<Film> filmPartecipanti) {
        this.filmPartecipanti = filmPartecipanti;
    }



    public List<Proiezione> getProiezioni() {
        return proiezioni;
    }



    public void setProiezioni(List<Proiezione> proiezioni) {
        this.proiezioni = proiezioni;
    }

    
}