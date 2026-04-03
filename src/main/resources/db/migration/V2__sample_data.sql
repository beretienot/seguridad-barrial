-- Propietarios de ejemplo
INSERT INTO propietarios (id, nombre, apellido, dni, direccion, telefono, email) VALUES
    ('a1b2c3d4-e5f6-7890-abcd-ef1234567801', 'Carlos',   'Gomez',     '28456123', 'Av. San Martín 450',       '3814501234', 'carlos.gomez@mail.com'),
    ('a1b2c3d4-e5f6-7890-abcd-ef1234567802', 'María',    'Rodriguez', '33789456', 'Calle Rivadavia 123',      '3814502345', 'maria.rodriguez@mail.com'),
    ('a1b2c3d4-e5f6-7890-abcd-ef1234567803', 'Jorge',    'Martinez',  '20123789', 'Bv. Independencia 78',     NULL,         NULL),
    ('a1b2c3d4-e5f6-7890-abcd-ef1234567804', 'Ana',      'Fernandez', '31654987', 'Pasaje Los Aromos 12',     '3814503456', 'ana.fernandez@mail.com'),
    ('a1b2c3d4-e5f6-7890-abcd-ef1234567805', 'Roberto',  'Lopez',     '25987321', 'Calle Córdoba 890',        '3814504567', NULL);

-- Propiedades de ejemplo
INSERT INTO propiedades (id, propietario_id, direccion) VALUES
    -- Carlos Gomez tiene 2 propiedades
    ('b1c2d3e4-f5a6-7890-bcde-fa1234567801', 'a1b2c3d4-e5f6-7890-abcd-ef1234567801', 'Av. San Martín 450'),
    ('b1c2d3e4-f5a6-7890-bcde-fa1234567802', 'a1b2c3d4-e5f6-7890-abcd-ef1234567801', 'Calle Tucumán 234 Dpto 3B'),
    -- María Rodriguez tiene 1 propiedad
    ('b1c2d3e4-f5a6-7890-bcde-fa1234567803', 'a1b2c3d4-e5f6-7890-abcd-ef1234567802', 'Calle Rivadavia 123'),
    -- Jorge Martinez tiene 3 propiedades
    ('b1c2d3e4-f5a6-7890-bcde-fa1234567804', 'a1b2c3d4-e5f6-7890-abcd-ef1234567803', 'Bv. Independencia 78'),
    ('b1c2d3e4-f5a6-7890-bcde-fa1234567805', 'a1b2c3d4-e5f6-7890-abcd-ef1234567803', 'Los Menhires 15 Casa 2'),
    ('b1c2d3e4-f5a6-7890-bcde-fa1234567806', 'a1b2c3d4-e5f6-7890-abcd-ef1234567803', 'Yerba Buena 560'),
    -- Ana Fernandez tiene 1 propiedad
    ('b1c2d3e4-f5a6-7890-bcde-fa1234567807', 'a1b2c3d4-e5f6-7890-abcd-ef1234567804', 'Pasaje Los Aromos 12');
    -- Roberto Lopez no tiene propiedades aún (caso 0 propiedades)
