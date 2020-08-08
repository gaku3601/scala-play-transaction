--- !Ups
create table messages
(
    id                      serial PRIMARY KEY,
    message                 varchar(255),
    user_name               varchar(255)
);

--- !Downs
drop table messages;