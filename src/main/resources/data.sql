INSERT INTO usuarios (username, nombre, apellido,
password, red_social, fecha_nacimiento, enabled, image)
VALUES
('luis.navarro@iudigital.edu.co', 'Luis Fernando', 'Navarro Mercado',
'', 0, '1997-01-28', 1, '');

INSERT INTO roles(nombre, descripcion)
VALUES('ROLE_ADMIN', 'administrador');

INSERT INTO roles(nombre, descripcion)
VALUES('ROLE_USER', 'usuario normal');

INSERT INTO roles_usuarios(usuarios_id, roles_id)
VALUES(1 , 1);

INSERT INTO roles_usuarios(usuarios_id, roles_id)
VALUES(1 , 2);

INSERT INTO delitos (nombre, descripcion, usuarios_id)
VALUES('Hurto', 'cuando le roban a una persona', 1);

INSERT INTO delitos (nombre, descripcion, usuarios_id)
VALUES('homicidio', 'cuando hace asesinan a alguien', 1);