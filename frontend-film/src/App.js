import React, { useState, useEffect } from 'react';
import './App.css'; // Mantieni il tuo CSS se ne hai uno

function App() {
  // Stati per memorizzare i dati e la stringa di ricerca
  const [films, setFilms] = useState([]);
  const [ricerca, setRicerca] = useState("");
  const [loading, setLoading] = useState(true);

  // useEffect scatta appena la pagina viene caricata
  useEffect(() => {
    // Chiamata all'API REST che hai appena creato in Spring Boot!
    fetch("http://localhost:8080/api/film")
      .then(response => {
        if (!response.ok) {
          throw new Error("Errore di rete");
        }
        return response.json();
      })
      .then(data => {
        setFilms(data);
        setLoading(false);
      })
      .catch(error => {
        console.error("Errore nel recupero dei film:", error);
        setLoading(false);
      });
  }, []); // L'array vuoto significa: fallo solo una volta all'avvio

  // Filtro in tempo reale: crea una nuova lista basata su quello che scrivi
  const filmFiltrati = films.filter(f =>
    f.titolo.toLowerCase().includes(ricerca.toLowerCase())
  );

  return (
    <div style={{ maxWidth: '800px', margin: '0 auto', padding: '20px', fontFamily: 'Arial, sans-serif' }}>
      <h1>🎬 Ricerca Film Interattiva (React)</h1>

      {/* Barra di ricerca interattiva */}
      <input
        type="text"
        placeholder="Cerca un film per titolo..."
        value={ricerca}
        onChange={(e) => setRicerca(e.target.value)}
        style={{
          width: '100%',
          padding: '12px',
          fontSize: '16px',
          marginBottom: '20px',
          borderRadius: '5px',
          border: '1px solid #ccc'
        }}
      />

      {loading ? (
        <p>Caricamento dati dal server in corso...</p>
      ) : (
        <div style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
          {filmFiltrati.length > 0 ? (
            filmFiltrati.map(film => (
              <div key={film.id} style={{ padding: '15px', border: '1px solid #eee', borderRadius: '8px', boxShadow: '0 2px 4px rgba(0,0,0,0.1)' }}>
                <h3 style={{ marginTop: '0' }}>{film.titolo} ({film.anno})</h3>
                <p><strong>Genere:</strong> {film.genere}</p>
                <p><strong>Durata:</strong> {film.durata} minuti</p>
                {/* Se vuoi mostrare anche il nome del regista, de-commenta la riga sotto: */}
                {/* <p><strong>Regista:</strong> {film.regista?.nome} {film.regista?.cognome}</p> */}
              </div>
            ))
          ) : (
            <p style={{ color: 'red' }}>Nessun film trovato con questo nome.</p>
          )}
        </div>
      )}
    </div>
  );
}

export default App;