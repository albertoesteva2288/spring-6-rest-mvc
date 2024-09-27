drop table if exists beer;
drop table if exists customer;
create table beer
(
    beer_style       smallint       not null,
    created_date     datetime,
    price            decimal(38, 2) not null,
    quantity_on_hand integer,
    updated_date     datetime,
    version          integer,
    id               varchar(36)    not null,
    beer_name        varchar(50)    not null,
    upc              varchar(255)   not null,
    primary key (id)
) engine = InnoDB;
create table customer
(
    created_date  datetime,
    updated_date  datetime,
    version       integer,
    id            varchar(36) not null,
    customer_name varchar(50) not null,
    primary key (id)
) engine = InnoDB;
