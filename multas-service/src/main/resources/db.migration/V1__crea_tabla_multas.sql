CREATE TABLE multas (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        clienteId BIGINT,
                        monto INT NOT NULL,
                        motivo VARCHAR(255),
                        estado VARCHAR(255),
                        fechaEmision DATE
);