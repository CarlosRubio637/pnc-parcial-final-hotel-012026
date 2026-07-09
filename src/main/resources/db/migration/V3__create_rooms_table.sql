con-- V3__create_rooms_table.sql
-- Crear tabla de habitaciones

CREATE TABLE rooms (
    id BIGSERIAL PRIMARY KEY,
    room_number VARCHAR(50) NOT NULL UNIQUE,
    room_type VARCHAR(50) NOT NULL,
    capacity INTEGER NOT NULL,
    price_per_night NUMERIC(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT check_positive_capacity CHECK (capacity > 0),
    CONSTRAINT check_positive_price CHECK (price_per_night > 0),
    CONSTRAINT check_valid_status CHECK (status IN ('AVAILABLE', 'OCCUPIED', 'MAINTENANCE'))
);

-- Crear índices para optimizar búsquedas
CREATE INDEX idx_room_status ON rooms(status);
CREATE INDEX idx_room_type ON rooms(room_type);
CREATE INDEX idx_room_number ON rooms(room_number);

