-- Roles iniciales del sistema.
-- Se usa el prefijo ROLE_ porque Spring Security lo exige por convención
-- cuando se usan expresiones como hasRole("ADMIN").
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_RECEPCIONISTA');
INSERT INTO roles (name) VALUES ('ROLE_HUESPED');