CREATE TABLE clientes (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          nombre VARCHAR(255) NOT NULL,
                          apellido VARCHAR(255) NOT NULL,
                          email VARCHAR(255) UNIQUE NOT NULL
);