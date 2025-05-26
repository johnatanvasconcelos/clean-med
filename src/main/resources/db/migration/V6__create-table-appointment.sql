create table appointments(

    id bigint generated always as identity primary key,
    doctor_id bigint not null,
    patient_id bigint not null,
    date_time timestamp not null,

    constraint fk_appointments_doctor_id foreign key (doctor_id) references doctors(id),
    constraint fk_appointments_patient_id foreign key (patient_id) references patients(id)

);