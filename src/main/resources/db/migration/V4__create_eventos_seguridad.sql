CREATE TABLE eventos_seguridad (
    id           UUID         NOT NULL,
    propiedad_id UUID         NOT NULL,
    tipo         VARCHAR(100) NOT NULL,
    descripcion  VARCHAR(500) NOT NULL,
    fecha_evento TIMESTAMP    NOT NULL,
    CONSTRAINT pk_eventos_seguridad PRIMARY KEY (id),
    CONSTRAINT fk_eventos_seguridad_propiedad
        FOREIGN KEY (propiedad_id)
        REFERENCES propiedades (id)
);
