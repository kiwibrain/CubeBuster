CREATE TABLE reclamos (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          motivo_reclamo VARCHAR(255) NOT NULL,
                          descripcion_reclamo VARCHAR(255) NOT NULL,
                          cliente_id BIGINT NOT NULL
);