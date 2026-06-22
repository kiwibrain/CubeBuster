CREATE TABLE reservas (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          nombre VARCHAR(255),
                          clienteId BIGINT NOT NULL,
                          idJuego BIGINT,
                          idPelicula BIGINT
);