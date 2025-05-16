alter table doctors add column active boolean;

update doctors set active = true;

alter table doctors alter column active SET NOT NULL;