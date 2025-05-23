create table patients(

    id bigint generated always as identity primary key,
    name varchar(200) not null,
    email varchar(100) not null unique,
    cpf varchar(11) not null unique,
    phone varchar(15) not null,
    active boolean not null,
    street varchar(100) not null,
    district varchar(100) not null,
    zip_code varchar(9) not null,
    city varchar(100) not null,
    state varchar(100) not null,
    additional_info varchar(200),
    number varchar(10)
);