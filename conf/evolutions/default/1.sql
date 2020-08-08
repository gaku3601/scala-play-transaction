--- !Ups
create table users
(
    id                      serial PRIMARY KEY,
    name                    varchar(255)
);

--- !Downs
drop table users;