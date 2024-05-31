create table if not exists users (
    id bigserial primary key not null,
    username varchar(255) not null unique,
    password varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    birthdate date not null,
    role varchar(10) not null default 'ROLE_USER'
);

create table if not exists items (
    id bigserial primary key not null,
    name varchar(255) not null,
    description varchar(255) default 'No description provided',
    price numeric not null default 0,
    vertices int not null default 0,
    image varchar(255) not null
);

create table if not exists comments (
    id bigserial primary key not null,
    comment varchar(255) not null,
    item_id bigint not null references items(id)
);