DROP SCHEMA IF EXISTS `620826`;
CREATE SCHEMA `620826`;
USE `620826`;

DROP TABLE IF EXISTS utente;
CREATE TABLE utente(
    username VARCHAR(100) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    punti INTEGER NOT NULL DEFAULT 0,
    bdate date not null,
    PRIMARY KEY(username, `password`)
)ENGINE=INNODB DEFAULT CHARSET=LATIN1;

DROP TABLE IF EXISTS piatto_pranzo;
CREATE TABLE piatto_pranzo(
    id_piatto INT NOT NULL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    ingredienti VARCHAR(255) NOT NULL,
    costo FLOAT NOT NULL
)ENGINE=INNODB DEFAULT CHARSET=LATIN1;

DROP TABLE IF EXISTS piatto_cena;
CREATE TABLE piatto_cena(
    id_piatto INT NOT NULL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    ingredienti VARCHAR(255) NOT NULL,
    costo FLOAT NOT NULL
)ENGINE=INNODB DEFAULT CHARSET=LATIN1;

DROP TABLE IF EXISTS ordine;
CREATE TABLE ordine(
    id_ordine INT NOT NULL PRIMARY KEY AUTO_INCREMENT PRIMARY KEY,
    utente VARCHAR(100) NOT NULL REFERENCES utente(username),
    orario_ordine TIMESTAMP NOT NULL,
    fascia_oraria VARCHAR(5) NOT NULL,
    indirizzo VARCHAR(100) NOT NULL,
    telefono VARCHAR(10) NOT NULL,
    totale FLOAT NOT NULL
)ENGINE=INNODB DEFAULT CHARSET=LATIN1;

SET GLOBAL event_scheduler = ON;

DROP EVENT IF EXISTS cancellaOrdini;
CREATE EVENT cancellaOrdini
ON SCHEDULE EVERY 1 DAY
STARTS concat(current_date(), ' 00:00')
DO TRUNCATE ordine;

DROP TABLE IF EXISTS preferiti;
CREATE TABLE preferiti(
    utente VARCHAR(255) NOT NULL,
    piatto INT NOT NULL,
    PRIMARY KEY(utente, piatto)
)ENGINE=INNODB DEFAULT CHARSET=LATIN1;

INSERT INTO utente(username, `password`, email, bdate) VALUES
	("test", "$2a$10$FEpH37Qn3m.ytKI5FCOWP.WvLh2.vyfDZ/QFy2yGOTQ/1pCL0bgym" /* Password = 'Proviamo1' */, "test@testmail.com", '2000-01-01')
;

INSERT INTO piatto_pranzo
VALUES
(1, "Edamame", "Fagioli giapponesi lessi", 3.85),
(2, "Wakame", "Alghe in salsa di sesamo", 4.4),
(3, "Involtini Primavera - 3 pezzi", "Involtini di verdura in pasta fritta con salsa agrodolce", 5.5),
(4, "Involtini Ebi - 3 pezzi", "Involtini di gamberi in pasta fritta con salsa agrodolce", 5.5),
(5, "Misto Mare", "Misto di pesce in salsa agrodolce", 7.5),
(6, "Tako seaweed", "Polpo condito con salsa di soya e alghe", 6.6),
(7, "Gyoza al vapore - 3 pezzi", "Ravioli di carne", 3.3),
(8, "Gyoza alla piastra - 3 pezzi", "Ravioli di carne", 3.3),
(9, "Gyoza di gamberi alla griglia - 3 pezzi", "Ravioli di gamberi", 3.3),
(10, "Tartare sake avocado", "Salmone e avocado", 7.3),
(11, "Tartare sake", "Salmone ", 6.3),
(12, "Sake Mandorle", "Salmone su riso e mandorle", 7.7),
(13, "Sake Tuna", "Tonno su riso", 6.6),
(14, "Onigiri Alga", "Riso, sesamo, alga", 3.2),
(15, "Onigiri Sake", "Riso, salmone", 3.6),
(16, "Onigiri Philadelphia", "Riso, salmone, philadelphia", 3.6),
(17, "Onigiri Tuna", "Riso, tonno, philadelphia", 3.6),
(18, "Onigiri Ebi", "Riso, gambero fritto, teriyaki, sesamo", 3.6),
(19, "Gunkan Sake", "Salmone, tobiko, maionese", 4.6),
(20, "Gunkan Tuna", "Tonno, tobiko, maionese", 4.6),
(21, "Gunkan spicy Tuna", "Tonno, tobiko, tabasco, maionese", 4.8),
(22, "Chirashi Sake", "Riso sushi ricoperto con salmone", 9.2),
(23, "Chirashi Tuna", "Riso sushi ricoperto con tonno", 9.2),
(24, "Sushi Misto - 20 pezzi", "6 Nigiri, 4 uramaki, 3 hosomaki, 5 sashimi, 2 gunkan", 20.2),
(25, "La Barca - 33 pezzi", "8 Nigiri, 8 uramaki, 6 hosomaki, 9 sashimi, 2 gunkan", 33.2),
(26, "Sushi Party Mix - 64 pezzi", "12 Nigiri, 24 uramaki, 12 hosomaki, 12 sashimi, 4 gunkan", 55.2),
(27, "Carpaccio Sake", "Salmone", 7.7),
(28, "Carpaccio Tuna", "Tonno", 7.4),
(29, "Carpaccio Pesce Bianco", "Ombrina", 8.3),
(30, "Tataki Salmone", "Salmone e sesamo", 9.9),
(31, "Tataki Tonno", "Tonno e sesamo", 9.9),
(32, "Sashimi Salmone", "Salmone crudo", 8.9),
(33, "Sashimi Tonno", "Tonno crudo", 8.9),
(34, "Sashimi Mix", "Tonno, salmone e ombrina crudi", 12.9),
(35, "Hosomaki Salmone", "Rotolino di riso avvolto in alga mori con ripieno di salmone", 4.95),
(36, "Hosomaki Tonno", "Rotolino di riso avvolto in alga mori con ripieno di tonno", 5.95),
(37, "Hosomaki Granchio", "Rotolino di riso avvolto in alga mori con ripieno di granchio", 4.95),
(38, "Hosomaki Gambero fritto", "Rotolino di riso avvolto in alga mori con ripieno di gamberetto fritto", 4.95),
(39, "Hosomaki Fritto con Fragole", "Hosomaki Salmone, fritto con topping di fragole", 6.05),
(40, "Hosomaki Fritto con Mango", "Hosomaki Salmone, fritto con topping di mango", 6.05),
(41, "Uramaki California - 8 pezzi", "Surimi, cetriolo, avocado, sesamo", 7.75),
(42, "Uramaki Sake - 8 pezzi", "Salmone, avocado, sesamo", 7.75),
(43, "Uramaki California - 8 pezzi", "Tonno, avocado, sesamo", 7.75),
(44, "Uramaki Philadelphia - 8 pezzi", "Salmone, philadelphia, sesamo", 7.75),
(45, "Uramaki Ebiten - 8 pezzi", "Gamberi fritti, maionese, kataifi, teriyaki, sesamo", 7.75),
(46, "Uramaki Tonno Cotto - 8 pezzi", "Tonno cotto, insalata, sesamo, salsa tonnata", 7.75),
(47, "Uramaki Miura - 8 pezzi", "Salmone alla griglia con philadelphia, insalata, sesamo, salsa teriyaki", 7.75),
(48, "Uramaki Lover - 8 pezzi", "Salmone, philadelphia, avocado, tobiko", 7.75),
(49, "Uramaki Tropicana - 8 pezzi", "Salmone alla griglia, philadelphia, granchio, tobiko", 7.75),
(50, "Gunkan Spcy Sake", "Salmone, tobiko, tabasco e maionese", 4.8)
;

INSERT INTO piatto_cena
SELECT * FROM piatto_pranzo;
INSERT INTO piatto_cena
VALUES 
(51, "Ebi Kataifi - 2 pezzi", "Fritto di gamberoni in pasta kataifi", 8.8),
(52, "Carpaccio New Style", "Misto carpaccio condito con salsa di soia e erba cipollina", 8.8),
(53, "Great Style sashimi", "Crudite` di pesce a cura dello Chef", 14.85),
(54, "Tris di Tartare", "Degustazione di tartare", 13.85),
(55, "Chokai Roll - 8 pezzi", "Uramaki, gambero fritto ricoperto di avocado, salmone, maionese piccante", 13.85)
;
