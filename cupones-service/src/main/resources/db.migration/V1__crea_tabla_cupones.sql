CREATE TABLE cupones (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         cliente_id BIGINT,
                         codigo VARCHAR(255),
                         porcentaje_descuento INT NOT NULL,
                         fecha_expiracion DATE
);