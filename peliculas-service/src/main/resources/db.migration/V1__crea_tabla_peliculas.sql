CREATE TABLE peliculas (
                           id_pelicula BIGINT PRIMARY KEY AUTO_INCREMENT,
                           nombre_pelicula VARCHAR(255) NOT NULL,
                           anio_pelicula INT NOT NULL,
                           precio_pelicula INT NOT NULL,
                           categoria_pelicula VARCHAR(255) NOT NULL
);