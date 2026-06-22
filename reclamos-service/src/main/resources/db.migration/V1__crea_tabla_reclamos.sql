CREATE TABLE reclamos (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          motivoReclamo VARCHAR(255) NOT NULL,
                          descripcionReclamo VARCHAR(255) NOT NULL,
                          clienteId BIGINT NOT NULL
);