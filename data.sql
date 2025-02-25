INSERT INTO "users" (id, username, password, mail) VALUES
    (nextval('user_sequence'), 'alice', '$2a$12$LjqcoW3zwuOichFLiTKGnOsfsOB4WOVYoxMIIoUdY14i928iTQxmm', 'alice@example.com'),
    (nextval('user_sequence'), 'bob',   '$2a$12$dzIUaNDhm9JM1YCjhUUTiOeEC65HeA47bgBnyYtKsebnA.BFm7Eje', 'bob@example.com'),
    (nextval('user_sequence'), 'eve',   '$2a$12$z0yunOS6Dcy9n70XXZNBCew8sbtltj2LPdsqFntTFAMmAlOzlSzT2', 'eve@example.com');

-- alice → Contraseña: password123
-- bob → Contraseña: securePass!
-- eve → Contraseña: mySecret#

-- conectar y cargar datos:
-- psql "host=pg-quo-nocountry-quo.f.aivencloud.com port=10146 dbname=defaultdb user=avnadmin password=AVNS_KcqlcHdz3CxN14rLrsv sslmode=require"
-- \i data.sql
-- SELECT * FROM users;