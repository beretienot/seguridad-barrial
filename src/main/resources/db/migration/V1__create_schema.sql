CREATE TABLE propietarios (
    id        UUID         NOT NULL,
    nombre    VARCHAR(100) NOT NULL,
    apellido  VARCHAR(100) NOT NULL,
    dni       VARCHAR(8)   NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    telefono  VARCHAR(50),
    email     VARCHAR(255),
    CONSTRAINT pk_propietarios PRIMARY KEY (id),
    CONSTRAINT uq_propietarios_dni UNIQUE (dni)
);

CREATE TABLE propiedades (
    id             UUID         NOT NULL,
    propietario_id UUID         NOT NULL,
    direccion      VARCHAR(255) NOT NULL,
    CONSTRAINT pk_propiedades PRIMARY KEY (id),
    CONSTRAINT fk_propiedades_propietario
        FOREIGN KEY (propietario_id)
        REFERENCES propietarios (id)
);
