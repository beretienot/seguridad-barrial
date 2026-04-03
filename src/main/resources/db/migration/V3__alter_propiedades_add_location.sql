ALTER TABLE propiedades ADD COLUMN provincia VARCHAR(100);
ALTER TABLE propiedades ADD COLUMN localidad VARCHAR(100);
ALTER TABLE propiedades ADD COLUMN latitud   NUMERIC(10, 7);
ALTER TABLE propiedades ADD COLUMN longitud  NUMERIC(10, 7);

-- Actualizar datos de ejemplo con provincia, localidad y coordenadas GPS (Tucumán, Argentina)
UPDATE propiedades SET provincia = 'Tucumán', localidad = 'San Miguel de Tucumán', latitud = -26.8241100, longitud = -65.2226500
WHERE id = 'b1c2d3e4-f5a6-7890-bcde-fa1234567801';

UPDATE propiedades SET provincia = 'Tucumán', localidad = 'San Miguel de Tucumán', latitud = -26.8303400, longitud = -65.2058700
WHERE id = 'b1c2d3e4-f5a6-7890-bcde-fa1234567802';

UPDATE propiedades SET provincia = 'Tucumán', localidad = 'San Miguel de Tucumán', latitud = -26.8198700, longitud = -65.2177300
WHERE id = 'b1c2d3e4-f5a6-7890-bcde-fa1234567803';

UPDATE propiedades SET provincia = 'Tucumán', localidad = 'San Miguel de Tucumán', latitud = -26.8241000, longitud = -65.2226000
WHERE id = 'b1c2d3e4-f5a6-7890-bcde-fa1234567804';

UPDATE propiedades SET provincia = 'Tucumán', localidad = 'Yerba Buena', latitud = -26.8080000, longitud = -65.3150000
WHERE id = 'b1c2d3e4-f5a6-7890-bcde-fa1234567805';

UPDATE propiedades SET provincia = 'Tucumán', localidad = 'Yerba Buena', latitud = -26.8122000, longitud = -65.3056000
WHERE id = 'b1c2d3e4-f5a6-7890-bcde-fa1234567806';

UPDATE propiedades SET provincia = 'Tucumán', localidad = 'San Miguel de Tucumán', latitud = -26.8315000, longitud = -65.2390000
WHERE id = 'b1c2d3e4-f5a6-7890-bcde-fa1234567807';
