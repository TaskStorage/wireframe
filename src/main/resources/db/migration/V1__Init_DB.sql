# CREATE DATABASE IF NOT EXISTS `storage`;
#
# Use `storage`;

create table hibernate_sequence (next_val bigint) engine=MyISAM;
insert into hibernate_sequence values ( 2 );

create table task (
  id bigint not null,
  content varchar(2048) not null,
  description varchar(2048) not null,
  filename varchar(255),
  user_id bigint,
  primary key (id)
) engine=MyISAM;

-- create table feed (
--   id integer not null,
--   giud varchar(255),
--   link varchar(2048),
--   title varchar(255),
--   pubdate varchar(255),
--   description varchar(1024),
--   primary key (id)
-- ) engine=MyISAM;

create table user_role (
  user_id bigint not null,
  roles varchar(255)
) engine=MyISAM;

create table usr (
  id bigint not null,
  activation_code varchar(255),
  active bit not null,
  email varchar(255),
  password varchar(255) not null,
  username varchar(255) not null,
  primary key (id)
) engine=MyISAM;

alter table task add constraint task_user_fk
foreign key (user_id) references usr (id);

alter table user_role add constraint user_role_user_fk
foreign key (user_id) references usr (id);