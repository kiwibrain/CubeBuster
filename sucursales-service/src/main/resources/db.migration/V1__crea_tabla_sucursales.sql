CREATE TABLE sucursales (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            nombre_sucursal VARCHAR(255) NOT NULL,
                            direccion_sucursal VARCHAR(255) NOT NULL,
                            telefono_sucursal VARCHAR(255),
                            horario_atencion VARCHAR(255)
);