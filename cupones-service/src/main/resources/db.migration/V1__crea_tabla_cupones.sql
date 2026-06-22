CREATE TABLE cupones (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         clienteId BIGINT,
                         codigo VARCHAR(255),
                         porcentajeDescuento INT NOT NULL,
                         fechaExpiracion DATE
);