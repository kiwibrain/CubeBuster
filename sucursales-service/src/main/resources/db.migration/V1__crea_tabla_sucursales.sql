CREATE TABLE sucursales (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            nombreSucursal VARCHAR(255) NOT NULL,
                            direccionSucursal VARCHAR(255) NOT NULL,
                            telefonoSucursal VARCHAR(255),
                            horarioAtencion VARCHAR(255)
);