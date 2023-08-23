create table user
(
    id            bigint auto_increment not null primary key,
    user_id       varchar(50)                            not null,
    pwd           varchar(255)                           not null,
    name          varchar(255)                           not null,
    role          varchar(2)   default '5' null,
    address       varchar(255) null,
    sex           varchar(2)   default '0' null,
    tel_no        varchar(255) null,
    email         varchar(255) null,
    department_id varchar(255) default '999' null,
    state         varchar(2)   default '0' null,
    employee_type varchar(2)   default '0' null,
    position      varchar(255) default '0' null,
    join_date     timestamp null,
    retire_date   timestamp null,
    updated_by    bigint                                 not null,
    updated_at    timestamp    default current_timestamp on update current_timestamp not null,
    created_by    bigint                                 not null,
    created_at    timestamp    default current_timestamp not null
);

create table department
(
    id         bigint auto_increment primary key,
    name       varchar(255)                        not null,
    created_at timestamp default current_timestamp not null,
    updated_at timestamp default current_timestamp on update current_timestamp not null
)