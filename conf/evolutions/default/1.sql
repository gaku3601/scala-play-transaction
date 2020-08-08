--- !Ups
create table users
(
    id   serial PRIMARY KEY,
    name varchar(255),
    age  integer
);

--- !Downs
drop table users;