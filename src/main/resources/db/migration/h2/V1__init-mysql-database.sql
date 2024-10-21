drop table if exists beer;
drop table if exists customer;
create table beer
(
    id               varchar(36)    not null,
    beer_name        varchar(50)    not null,
    beer_style       smallint       not null,
    price            decimal(38, 2) not null,
    quantity_on_hand integer,
    version          integer,
    upc              varchar(255)   not null,
    created_date     datetime(6),
    updated_date     datetime(6),
    primary key (id)
);
create table customer
(
    id            varchar(36) not null,
    customer_name varchar(50) not null,
    version       integer,
    created_date  datetime(6),
    updated_date  datetime(6),
    primary key (id)
);
