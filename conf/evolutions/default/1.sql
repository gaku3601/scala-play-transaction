--- !Ups
create table users
(
    id         bigserial PRIMARY KEY,
    name       varchar(255),
    age        integer,
    updated_at timestamp not null default current_timestamp,
    created_at timestamp not null default current_timestamp
);

--- !Downs
drop table users;