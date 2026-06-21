CREATE TABLE resenas (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         cliente_id BIGINT,
                         id_juego BIGINT,
                         id_pelicula BIGINT,
                         nombre_item VARCHAR(255),
                         descripcion_resena VARCHAR(255) NOT NULL,
                         nickname_cliente VARCHAR(255)
);