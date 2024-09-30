drop table if exists beer_order_line;
drop table if exists beer_order;
create table beer_order
(
    id               varchar(36)    not null,
    version          integer,
    customer_ref     varchar(255),
    customer_id      varchar(36)    not null,
    created_date     datetime(6),
    updated_date     datetime(6),
    primary key (id)
) engine = InnoDB;

create table beer_order_line
(
    id                    varchar(36) not null,
    beer_order_id         varchar(36) not null,
    beer_id               varchar(36) not null,
    order_quantity        integer,
    quantity_allocated    integer,
    version               integer,
    created_date          datetime(6),
    updated_date          datetime(6),
    primary key (id)
) engine = InnoDB;

ALTER TABLE beer_order
    ADD CONSTRAINT fk_customer
        FOREIGN KEY (customer_id) REFERENCES customer(id);

ALTER TABLE beer_order_line
    ADD CONSTRAINT fk_beer_order
        FOREIGN KEY (beer_order_id) REFERENCES beer_order(id);

ALTER TABLE beer_order_line
    ADD CONSTRAINT fk_beer
        FOREIGN KEY (beer_id) REFERENCES beer(id);

ALTER TABLE beer_order_line
    ADD CONSTRAINT unique_beer_order_beer
       UNIQUE (beer_order_id, beer_id);