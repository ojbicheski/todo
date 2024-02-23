create schema if not exists todo_schema;

create table  if not exists todo_schema.tb_todo(
	id bigint not null auto_increment,
	content varchar(500) not null,
	completed boolean not null default false,
	primary key(id),
	unique(content)
);