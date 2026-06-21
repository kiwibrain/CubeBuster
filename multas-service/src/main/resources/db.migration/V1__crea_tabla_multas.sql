CREATE TABLE multas (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        cliente_id BIGINT,
                        monto INT NOT NULL,
                        motivo VARCHAR(255),
                        estado VARCHAR(255),
                        fecha_emision DATE
);