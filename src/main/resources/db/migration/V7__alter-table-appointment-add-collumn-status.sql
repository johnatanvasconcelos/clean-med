alter table appointments add column status varchar(15);

update appointments set status = 'SCHEDULED' where status IS NULL;

alter table appointments alter column status set not null;