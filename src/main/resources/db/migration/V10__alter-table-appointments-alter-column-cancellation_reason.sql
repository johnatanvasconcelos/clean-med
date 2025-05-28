alter table appointments
alter column cancellation_reason drop default;

alter table appointments
alter column cancellation_reason drop not null;

update appointments
set cancellation_reason = null
where cancellation_reason = 'N/A';