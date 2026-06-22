CREATE TABLE peliculas (
                           idPelicula BIGINT PRIMARY KEY AUTO_INCREMENT,
                           nombrePelicula VARCHAR(255) NOT NULL,
                           anioPelicula INT NOT NULL,
                           precioPelicula INT NOT NULL,
                           categoriaPelicula VARCHAR(255) NOT NULL
);