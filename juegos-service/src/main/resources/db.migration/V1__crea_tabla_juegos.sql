CREATE TABLE juegos (
                        idJuego BIGINT PRIMARY KEY AUTO_INCREMENT,
                        nombreJuego VARCHAR(255) NOT NULL,
                        anioJuego INT NOT NULL,
                        precioJuego INT NOT NULL,
                        categoriaJuego VARCHAR(255) NOT NULL
);