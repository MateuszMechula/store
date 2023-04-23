drop table if exists opinion;
drop table if exists purchase;
drop table if exists product;
drop table if exists producer;
drop table if exists customer;


CREATE TABLE customer(
    id                 SERIAL      NOT NULL,
    user_name          VARCHAR(64) NOT NULL,
    email              VARCHAR(64) NOT NULL,
    name               VARCHAR(64) NOT NULL,
    surname            VARCHAR(64) NOT NULL,
    date_of_birth      DATE,
    telephone_number   VARCHAR(64),
    PRIMARY KEY(id),
    UNIQUE (user_name),
    UNIQUE (email)
)

CREATE TABLE producer(
    id                 SERIAL       NOT NULL,
    producer_name      VARCHAR(64)  NOT NULL,
    address            VARCHAR(128) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE (producer_name)
)

CREATE TABLE product(
    id                  SERIAL          NOT NULL,
    product_code        VARCHAR(64)     NOT NULL,
    product_name        VARCHAR(64)     NOT NULL,
    product_price       NUMERIC(7,2)    NOT NULL,
    adults_only         BOOLEAN         NOT NULL,
    description         TEXT            NOT NULL,
    producer_id         INT             NOT NULL,
    PRIMARY KEY(id),
    UNIQUE (product_code),
    CONSTRAINT fk_product_producer
        FOREIGN KEY (producer_id)
            REFERENCES producer (id)
)

CREATE TABLE purchase (
    id                 SERIAL                   NOT NULL,
    customer_id        INT                      NOT NULL,
    product_id         INT                      NOT NULL,
    quantity           INT                      NOT NULL,
    date_time          TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_purchase_product
            FOREIGN KEY (product_id)
                REFERENCES product (id),
    CONSTRAINT fk_purchase_customer
                FOREIGN KEY (customer_id)
                    REFERENCES customer (id)
)
CREATE TABLE opinion(
    id                 SERIAL                          NOT NULL,
    customer_id        INT                             NOT NULL,
    product_id         INT                             NOT NULL,
    stars              INT CHECK(stars IN (1,2,3,4,5)) NOT NULL,
    comment            TEXT                            NOT NULL,
    date_time          TIMESTAMP WITH TIME ZONE        NOT NULL,
    PRIMARY KEY(id),
        CONSTRAINT fk_purchase_product
                FOREIGN KEY (product_id)
                    REFERENCES product (id),
        CONSTRAINT fk_purchase_customer
                    FOREIGN KEY (customer_id)
                        REFERENCES customer (id)
)