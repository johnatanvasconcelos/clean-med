create table doctors(

     id bigint generated always as identity primary key,
    name varchar(120) not null,
    email varchar(100) not null unique,
    crm varchar(6) not null unique,
    specialty varchar(100) not null,
    street varchar(100) not null,
    district varchar(100) not null,
    zip_code varchar(9) not null,
    city varchar(100) not null,
    state varchar(100) not null,
    additional_info varchar(200),
    number varchar(10)
);