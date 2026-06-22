CREATE TABLE pagos (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       clienteId BIGINT,
                       monto INT NOT NULL,
                       metodoPago VARCHAR(255),
                       fechaPago DATE
);