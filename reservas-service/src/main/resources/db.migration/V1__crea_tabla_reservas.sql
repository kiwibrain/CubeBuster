CREATE TABLE reservas (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          nombre VARCHAR(255),
                          cliente_id BIGINT NOT NULL,
                          id_juego BIGINT,
                          id_pelicula BIGINT
);