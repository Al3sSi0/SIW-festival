package it.uniroma3.siw.film;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.film.model.Festival;
import it.uniroma3.siw.film.model.Film;
import it.uniroma3.siw.film.repository.FestivalRepository;
import jakarta.persistence.EntityManagerFactory;

@SpringBootTest
public class PrestazioniAccessoDatiTest {

    @Autowired
    private FestivalRepository festivalRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    @Transactional
    public void confrontaStrategieAccessoDati() {
        // ID di un festival che sai esistere nel database e che ha dei film associati
        // (Assicurati di avere dati nel DB quando esegui il test all'orale!)
        Long idFestival = 1L; 

        // Recuperiamo l'oggetto Statistics di Hibernate per contare le query SQL
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Statistics stats = sessionFactory.getStatistics();
        stats.setStatisticsEnabled(true);

        System.out.println("\n=== Test accesso ai film del festival ===");

        // ---------------------------------------------------------
        // STRATEGIA 1: LAZY (Comportamento di default)
        // ---------------------------------------------------------
        stats.clear(); // Resetta il contatore delle query
        long startLazy = System.currentTimeMillis();

        // 1. Carica il festival (1 query)
        Festival festivalLazy = festivalRepository.findById(idFestival).orElse(null);
        int countFilm = 0;
        
        if (festivalLazy != null) {
            // 2. Il fetch LAZY scatterà qui! Per ogni film, farà una query per caricare il Regista (N+1)
            for (Film f : festivalLazy.getFilmPartecipanti()) {
                f.getRegista().getNome(); // Forza il caricamento del regista
                countFilm++;
            }
        }

        long tempoLazy = System.currentTimeMillis() - startLazy;
        long queryLazy = stats.getPrepareStatementCount();

        System.out.println("Strategia 1: LAZY");
        System.out.println("Film caricati: " + countFilm);
        System.out.println("Query SQL: " + queryLazy);
        System.out.println("Tempo: " + tempoLazy + " ms\n");

        // ---------------------------------------------------------
        // STRATEGIA 2: JOIN FETCH
        // ---------------------------------------------------------
        stats.clear(); // Resetta di nuovo il contatore
        long startFetch = System.currentTimeMillis();

        // 1. Carica il festival, i film e i registi tutti insieme in 1 SOLA QUERY
        Festival festivalFetch = festivalRepository.findByIdConFilmERegisti(idFestival);
        int countFilmFetch = 0;
        
        if (festivalFetch != null) {
            for (Film f : festivalFetch.getFilmPartecipanti()) {
                f.getRegista().getNome(); // I dati sono già in memoria, non fa nessuna query extra!
                countFilmFetch++;
            }
        }

        long tempoFetch = System.currentTimeMillis() - startFetch;
        long queryFetch = stats.getPrepareStatementCount();

        System.out.println("Strategia 2: JOIN FETCH");
        System.out.println("Film caricati: " + countFilmFetch);
        System.out.println("Query SQL: " + queryFetch);
        System.out.println("Tempo: " + tempoFetch + " ms\n");
    }
}