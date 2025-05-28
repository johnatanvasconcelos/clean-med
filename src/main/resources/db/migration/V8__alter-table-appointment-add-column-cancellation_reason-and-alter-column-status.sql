alter table appointments
rename column status to appointment_status;

alter table appointments
add column cancellation_reason varchar(255) default 'N/A' not null;