CREATE TABLE pagos (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       cliente_id BIGINT,
                       monto INT NOT NULL,
                       metodo_pago VARCHAR(255),
                       fecha_pago DATE
);