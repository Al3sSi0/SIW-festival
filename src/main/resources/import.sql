INSERT INTO utente (id, username, password, ruolo) VALUES (nextval('utente_seq'), 'admin', '{noop}admin', 'ADMIN');
INSERT INTO regista (id, nome, cognome, data_nascita, nazionalita) VALUES (nextval('regista_seq'), 'Christopher', 'Nolan', '1970-07-30', 'Britannica');

INSERT INTO film (id, titolo, anno, durata, genere, paese_produzione, regista_id, url_immagine) VALUES (nextval('film_seq'), 'Inception', 2010, 148, 'Fantascienza', 'USA', currval('regista_seq'), 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSsXv8nE0Zvz6zOA3u-qMAUXVp40_C-BqhCSJTxijdufQ&s=10');

INSERT INTO festival (id, nome, citta, anno) VALUES (nextval('festival_seq'), 'Festival di Cannes', 'Cannes', 2026);

INSERT INTO sala (id, nome, indirizzo, capienza) VALUES (nextval('sala_seq'), 'Sala Lumière', 'Boulevard de la Croisette', 2000);

INSERT INTO utente (id, username, password, ruolo) VALUES (nextval('utente_seq'), 'mario', '{noop}user', 'USER');

INSERT INTO regista (id, nome, cognome, data_nascita, nazionalita) VALUES (nextval('regista_seq'), 'Leitch', 'David', '1975-11-16', 'Americana');

INSERT INTO film (id, titolo, anno, durata, genere, paese_produzione, regista_id, url_immagine) VALUES (nextval('film_seq'), 'Deadpool 2', 2018, 119, 'Supereroi', 'USA', currval('regista_seq'), 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSz5kvR1P6vHKsCCfmhwpR3cfTGo9OqRrmqojkxOsQWYw&s=10');

INSERT INTO film (id, titolo, anno, durata, genere, paese_produzione, regista_id, url_immagine) VALUES (nextval('film_seq'), 'Interstellar', 2014, 169, 'Fantascienza', 'USA', (SELECT id FROM regista WHERE nome = 'Christopher' AND cognome = 'Nolan' LIMIT 1), 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4qy4v9p6Uf5GWrRjQaV1BYrKlD7jPYdufQ859sGhX0w&s=10');

INSERT INTO utente (id, username, password, ruolo) VALUES (nextval('utente_seq'), 'ale', '{noop}user', 'USER');