create table users(

    id bigint generated always as identity primary key,
    login varchar(120) not null,
    password varchar(255) not null
);