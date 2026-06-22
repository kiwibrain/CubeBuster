CREATE TABLE resenas (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         clienteId BIGINT,
                         idJuego BIGINT,
                         idPelicula BIGINT,
                         nombreItem VARCHAR(255),
                         descripcionResena VARCHAR(255) NOT NULL,
                         nicknameCliente VARCHAR(255)
);