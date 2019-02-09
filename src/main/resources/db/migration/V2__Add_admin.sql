# Use `storage`;

insert into usr (id, username, password, active)
  values (1, 'admin', '$2a$08$rjr6P1uyUDsvNqj4U9oYfOCz.3LzeKowIuJxmsL..a7ojl3quKyL2', true);

insert into user_role (user_id, roles)
values (1, 'USER'), (1, 'ADMIN');