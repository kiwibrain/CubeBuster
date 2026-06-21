CREATE TABLE juegos (
                        id_juego BIGINT PRIMARY KEY AUTO_INCREMENT,
                        nombre_juego VARCHAR(255) NOT NULL,
                        anio_juego INT NOT NULL,
                        precio_juego INT NOT NULL,
                        categoria_juego VARCHAR(255) NOT NULL
);